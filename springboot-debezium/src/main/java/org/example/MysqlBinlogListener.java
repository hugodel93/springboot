package org.example;

import io.debezium.config.Configuration;
import io.debezium.data.Envelope;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.example.util.JsonUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/***
 * @Title MysqlBinlogListener
 * @Description:
 * @author: hang.hu
 * @date: 2023/8/11 下午6:02
 * @version: 1.0.0
 **/
@Component
public class MysqlBinlogListener {
    @Resource
    private Executor taskExecutor;

    private final List<DebeziumEngine<ChangeEvent<String, String>>> engineList = new ArrayList<>();

    public MysqlBinlogListener(@Qualifier("mysqlConnector") Configuration configuration) {
        this.engineList.add(DebeziumEngine.create(Json.class)
                .using(configuration.asProperties())
                .notifying(record -> receiveChangeEvent(record.value()))
                .build());
    }

    @PostConstruct
    private void start() {
        for (DebeziumEngine<ChangeEvent<String, String>> engine : engineList) {
            taskExecutor.execute(engine);
        }
    }

    @PreDestroy
    private void stop() {
        for (DebeziumEngine<ChangeEvent<String, String>> engine : engineList) {
            if (engine != null) {
                try {
                    engine.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void receiveChangeEvent(String value) {
        if (Objects.nonNull(value)) {
            Map<String, Object> payload = getPayload(value);
            String op = JsonUtil.toJsonString(payload.get("op"));
            if (!(StringUtils.isBlank(op) || Envelope.Operation.READ.equals(op))) {
                ChangeData changeData = getChangeData(payload);
                System.out.println("changeData = " + changeData);
            }
        }
    }

    private static Map<String, Object> getPayload(String value) {
        Map<String, Object> map = JsonUtil.toMap(value);
        Map<String, Object> payload = JsonUtil.toMap(JsonUtil.toJsonString(map.get("payload")));
        return payload;
    }

    private static ChangeData getChangeData(Map<String, Object> payload) {
        Map<String, Object> source = JsonUtil.toMap(JsonUtil.toJsonString(payload.get("source")));
        return ChangeData.builder()
                .op(payload.get("op").toString())
                .table(source.get("table").toString())
                .after(JsonUtil.toMap(JsonUtil.toJsonString(payload.get("after"))))
                .source(JsonUtil.toMap(JsonUtil.toJsonString(payload.get("source"))))
                .before(JsonUtil.toMap(JsonUtil.toJsonString(payload.get("before"))))
                .build();
    }

    @Data
    @Builder
    static class ChangeData {
        /**
         * 更改前数据
         */
        private Map<String, Object> after;
        private Map<String, Object> source;
        /**
         * 更改后数据
         */
        private Map<String, Object> before;
        /**
         * 更改的表名
         */
        private String table;
        /**
         * 操作类型, 枚举 Envelope.Operation
         */
        private String op;

    }
}
