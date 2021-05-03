package com.fudanuniversity.cms.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * redis存储会话
 * <p>
 * Created by tidu at 2021-05-01 22:01:21
 *
 * @see RedisHttpSessionConfiguration
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 45 * 100/*TODO 修改会话时间*/, redisNamespace = "fd:cms:session")
public class RedisSessionConfig {

    /**
     * @see RedisHttpSessionConfiguration#setDefaultRedisSerializer(org.springframework.data.redis.serializer.RedisSerializer)
     */
    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> jackson2JsonSerializer() {
        DefaultJackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new DefaultJackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = JsonUtils.createGeneralObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会异常
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        return jackson2JsonRedisSerializer;
    }

    /**
     * 会话cookie使用的key约定为 sid
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("sid");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return serializer;
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    static final byte[] EMPTY_ARRAY = new byte[0];

    static boolean isEmpty(@Nullable byte[] data) {
        return (data == null || data.length == 0);
    }

    /**
     * {@link RedisSerializer} that can read and write JSON using
     * <a href="https://github.com/FasterXML/jackson-core">Jackson's</a> and
     * <a href="https://github.com/FasterXML/jackson-databind">Jackson Databind</a> {@link ObjectMapper}.
     * <p>
     * This converter can be used to bind to typed beans, or untyped {@link java.util.HashMap HashMap} instances.
     * <b>Note:</b>Null objects are serialized as empty arrays and vice versa.
     *
     * @author Thomas Darimont
     * @since 1.2
     */
    static class DefaultJackson2JsonRedisSerializer<T> implements RedisSerializer<T> {

        private final JavaType javaType;

        private ObjectMapper objectMapper = new ObjectMapper();

        /**
         * Creates a new {@link org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer} for the given target {@link Class}.
         *
         * @param type
         */
        public DefaultJackson2JsonRedisSerializer(Class<T> type) {
            this.javaType = getJavaType(type);
        }

        /**
         * Creates a new {@link org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer} for the given target {@link JavaType}.
         *
         * @param javaType
         */
        public DefaultJackson2JsonRedisSerializer(JavaType javaType) {
            this.javaType = javaType;
        }

        @SuppressWarnings("unchecked")
        public T deserialize(@Nullable byte[] bytes) throws SerializationException {
            if (isEmpty(bytes)) {
                return null;
            }
            try {
                return (T) this.objectMapper.readValue(bytes, 0, bytes.length, javaType);
            } catch (Exception ex) {
                return null;    //Note: 序列化失败可能是改了序列化对象的包名什么的，这里做优化处理，返回null，不一定最合理但是能规避烦人问题
                //throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
            }
        }

        @Override
        public byte[] serialize(@Nullable Object t) throws SerializationException {

            if (t == null) {
                return EMPTY_ARRAY;
            }
            try {
                return this.objectMapper.writeValueAsBytes(t);
            } catch (Exception ex) {
                throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
            }
        }

        /**
         * Sets the {@code ObjectMapper} for this view. If not set, a default {@link ObjectMapper#ObjectMapper() ObjectMapper}
         * is used.
         * <p>
         * Setting a custom-configured {@code ObjectMapper} is one way to take further control of the JSON serialization
         * process. For example, an extended {@link SerializerFactory} can be configured that provides custom serializers for
         * specific types. The other option for refining the serialization process is to use Jackson's provided annotations on
         * the types to be serialized, in which case a custom-configured ObjectMapper is unnecessary.
         */
        public void setObjectMapper(ObjectMapper objectMapper) {

            Assert.notNull(objectMapper, "'objectMapper' must not be null");
            this.objectMapper = objectMapper;
        }

        /**
         * Returns the Jackson {@link JavaType} for the specific class.
         * <p>
         * Default implementation returns {@link TypeFactory#constructType(java.lang.reflect.Type)}, but this can be
         * overridden in subclasses, to allow for custom generic collection handling. For instance:
         *
         * <pre class="code">
         * protected JavaType getJavaType(Class&lt;?&gt; clazz) {
         * 	if (List.class.isAssignableFrom(clazz)) {
         * 		return TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, MyBean.class);
         *    } else {
         * 		return super.getJavaType(clazz);
         *    }
         * }
         * </pre>
         *
         * @param clazz the class to return the java type for
         * @return the java type
         */
        protected JavaType getJavaType(Class<?> clazz) {
            return TypeFactory.defaultInstance().constructType(clazz);
        }
    }
}
