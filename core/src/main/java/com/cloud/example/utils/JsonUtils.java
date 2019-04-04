package com.cloud.example.utils;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    static {
        //Null 处理为 空字符
        mapper.configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
        //字段为NULL或者""的时候不会列入
//        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    }

    /**
     * JSON转Object
     *
     * @param string
     * @param clazz
     * @return
     */
    public static <T> T toObject(String string, Class<T> clazz) {
        T t = null;
        try {
            t = mapper.readValue(string, clazz);
        } catch (Exception e) {
            logger.error("json convert error " + e);
            throw new RuntimeException("json convert error");
        }

        return t;
    }

    /**
     * Object转JSON
     *
     * @param <T>
     * @param obj
     * @return
     */
    public static <T> String toJson(T obj) {
        String value = null;
        try {
            value = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("json convert error " + e);
            throw new RuntimeException("json convert error");
        }
        return value;
    }


    public static <T> T toObject(String string, TypeReference<T> type) {
        T t = null;
        try {
            mapper.enableDefaultTyping();
            t = mapper.readValue(string, type);
        } catch (Exception e) {
            logger.error("json convert error " + e);
            throw new RuntimeException("json convert error");
        }
        return t;
    }

    /**
     * @param string
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String string, Class<T> clazz) {

        List<T> list = new ArrayList<T>(8);
        try {
            list = mapper.readValue(string, mapper.getTypeFactory().constructParametricType(ArrayList
                    .class, clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
