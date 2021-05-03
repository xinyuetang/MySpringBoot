package com.fudanuniversity.cms.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Xinyue.Tang at 2021-04-14 16:15:15
 */
public final class JsonUtils {

    public final static Type StringObjectMapType = new TypeReference<Map<String, Object>>() {
    }.getType();
    public final static Type IntegerListType = new TypeReference<List<Integer>>() {
    }.getType();
    public final static Type StringListType = new TypeReference<List<String>>() {
    }.getType();

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    public static final String DATETIME_MILLISECOND_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final DateTimeFormatter DATETIME_MILLISECOND_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_MILLISECOND_PATTERN);

    private final static ObjectMapper MAPPER;

    private final static JavaType StringObjectMapJavaType;
    private final static JavaType IntegerListJavaType;
    private final static JavaType StringListJavaType;

    static {
        MAPPER = createGeneralObjectMapper();
        StringObjectMapJavaType = MAPPER.getTypeFactory().constructType(StringObjectMapType);
        IntegerListJavaType = MAPPER.getTypeFactory().constructType(IntegerListType);
        StringListJavaType = MAPPER.getTypeFactory().constructType(StringListType);
    }

    public static ObjectMapper createGeneralObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //Date类型文本格式化
        mapper.setDateFormat(new SimpleDateFormat(DATETIME_PATTERN));
        //JDK新时间序列化时不要带有T字符
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        mapper.registerModule(javaTimeModule);
        mapper.setTimeZone(TimeZone.getDefault());
        //不要序列化null值
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //JSON串含有未知字段时，反序列化不要失败
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //默认开启：如果一个类没有public的方法或属性时，会得到一个空JSON串，而不是序列化失败
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //BigDecimal不输出科学计数法的值
        mapper.getFactory().configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        return mapper;
    }

    public static <T> T parseObject(String json, Type type) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructType(type));
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> typeRef) {
        try {
            return MAPPER.readValue(json, typeRef);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

    public static Map<String, Object> parseAsMap(String json) {
        try {
            return MAPPER.readValue(json, StringObjectMapJavaType);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

    public static List<Integer> parseAsIntegerList(String json) {
        try {
            return MAPPER.readValue(json, IntegerListJavaType);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

    public static List<String> parseAsStringList(String json) {
        try {
            return MAPPER.readValue(json, StringListJavaType);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

    public static String toJsonString(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

    private JsonUtils() {
    }
}
