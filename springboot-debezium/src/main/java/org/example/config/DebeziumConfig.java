package org.example.config;


import io.debezium.connector.mysql.MySqlConnector;
import io.debezium.relational.history.FileDatabaseHistory;
import lombok.Data;
import org.apache.kafka.connect.storage.FileOffsetBackingStore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/***
 * @Title MysqlBinlogConfig
 * @Description:
 * @author: hang.hu
 * @date: 2023/8/11 下午5:37
 * @version: 1.0.0
 **/
@Configuration
@ConfigurationProperties(prefix ="debezium.datasource")
@Data
public class DebeziumConfig {
    private String hostname;
    private String port;
    private String user;
    private String password;
    private String tableWhitelist;
    private String storageFile;
    private String historyFile;
    private Long flushInterval;
    private String serverId;
    private String serverName;

    @Bean(value = "mysqlConnector")
    public io.debezium.config.Configuration configuration() throws Exception{
        return io.debezium.config.Configuration.create()
                .with("name", "mysql_connector")
                //监控的数据库类型，这里选mysql
                .with("connector.class", MySqlConnector.class)
                // .with("offset.storage", KafkaOffsetBackingStore.class) //使用kafka
                //把读取进度存到本地文件
                .with("offset.storage", FileOffsetBackingStore.class)
                //存放读取进度的本地文件地址
                .with("offset.storage", "org.apache.kafka.connect.storage.MemoryOffsetBackingStore")
//                .with("offset.storage.file.filename", storageFile)
                //读取进度刷新保存频率，默认1分钟
                .with("offset.flush.interval.ms", flushInterval)
//                .with("database.history", FileDatabaseHistory.class.getName())
                .with("database.history", "io.debezium.relational.history.MemoryDatabaseHistory")
                .with("database.history.file.filename", historyFile)
                //快照模式，指定连接器启动时运行快照的条件
                .with("snapshot.mode", "Schema_only")
                //伪装成slave的Debezium服务的id，自定义，有多个Debezium服务不能重复，如果重复的话会报以下异常。
                .with("database.server.id", serverId)
                .with("database.server.name", serverName)
                .with("database.hostname", hostname)
//                .with("database.dbname", dbname)
                .with("database.port", port)
                .with("database.user", user)
                .with("database.password", password)
                //监控的数据库白名单,如果选此值，会忽略table.whitelist，然后监控此db下所有表的binlog
//                .with("database.whitelist", "test")
                //监控的表名白名单,建议设置此值，只监控这些表的binlog
                .with("table.whitelist", tableWhitelist)
                .build();

    }

    private void checkFile() throws IOException {
        String dir = storageFile.substring(0, storageFile.lastIndexOf("/"));
        File dirFile = new File(dir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        File file = new File(storageFile);
        if(!file.exists()){
            file.createNewFile();
        }
    }
}
