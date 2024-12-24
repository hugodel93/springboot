package org.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @description: 基于jackson的二次封装
 * @author: hang.hu
 * @create: 2022-03-04
 **/
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper;

    // 日期格式化
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
    }

    /**
     * Json转换Map<String, String>对象
     *
     * @param json Json字符串
     * @return Map<String, String>
     */
    public static <K, V> Map<K, V> toMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<K, V>>() {});
        } catch (IOException e) {
            logger.error("toMap 出错 -> {}", json, e);
            return null;
        }
    }

    /**
     * Json转换Map<String, String>对象
     *
     * @param json Json字符串
     * @return Map<String, String>
     */
    public static <K, V> List<Map<K, V>> toMapList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Map<K, V>>>() {});
        } catch (IOException e) {
            logger.error("toMap 出错 -> {}", json, e);
            return null;
        }
    }


    /**
     * Json转换List<Map<String, String>>对象
     *
     * @param json Json字符串
     * @return List<Map < String, String>>
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<T>>() {});
        } catch (IOException e) {
            logger.error("toList 出错 -> {}", json, e);
            return null;
        }
    }

    /**
     * 生成Json，null值转换为空字符串
     *
     * @param object 待转换的Object
     * @return Json
     */
    public static String toJsonString(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("toJsonString 出错 -> {}", object, e);
            return null;
        }
    }

    /**
     * 解析Json
     *
     * @param jsonString 待Json字符串
     * @param clazz      转换的类型
     * @param <T>        对象
     * @return T
     * @throws IOException
     */
    public static <T> T toBean(String jsonString, Class<T> clazz) {

        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            logger.error("toBean出错,jsonString -> {}, class -> {}", jsonString, clazz.getName(), e);
            return null;
        }
    }

}
