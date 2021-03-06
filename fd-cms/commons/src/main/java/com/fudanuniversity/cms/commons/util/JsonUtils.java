package com.fudanuniversity.cms.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        //Date????????????????????????
        mapper.setDateFormat(new SimpleDateFormat(DATETIME_PATTERN));
        //???????????????Module
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new DynamicDateDeserializer());//??????????????????????????????????????????
        mapper.registerModule(module);
        //JDK?????????????????????????????????T??????
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        mapper.registerModule(javaTimeModule);
        mapper.setTimeZone(TimeZone.getDefault());
        //???????????????null???
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //JSON???????????????????????????????????????????????????
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //????????????????????????????????????public??????????????????????????????????????????JSON??????????????????????????????
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //BigDecimal?????????????????????????????????
        mapper.getFactory().configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        return mapper;
    }

    /**
     * json????????????java.util.Date??????????????????
     * <ul>
     *   <li>??????????????????????????????1599235200000</li>
     *   <li>????????????????????????yyyy-MM-dd</li>
     *   <li>??????????????????????????????yyyy-MM-dd HH:mm:ss</li>
     *   <li>????????????????????????????????????yyyy-MM-dd HH:mm:ss.SSS</li>
     * </ul>
     */
    static class DynamicDateDeserializer extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException {
            return DateExUtils.parseDynamicFormat(jsonParser.getText());
        }
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
