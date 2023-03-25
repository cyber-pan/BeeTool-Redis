package org.beetool.redis.robin.loop.queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 脚本参数序列化工具
 *
 * @param <T>
 */
public class FastJson2JsonRedisParamSerializer<T> implements RedisSerializer<T> {
    @SuppressWarnings("unused")
    private ObjectMapper objectMapper = new ObjectMapper();

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    /**
     * 基础类型参数
     */
    private static final String[] BASE_CLASS_NAMES = {"java.lang.Long", "java.lang.Integer", "java.lang.Double", "java.lang.String"};

    private Class<T> clazz;

    static {
        ParserConfig.getGlobalInstance().setAsmEnable(true);
    }


    public FastJson2JsonRedisParamSerializer() {
        super();
    }

    /**
     * @param deserializeClazz ：反序列化类型
     */
    public FastJson2JsonRedisParamSerializer(Class<T> deserializeClazz) {
        super();
        this.clazz = deserializeClazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        /**
         * 统一将参数类型转成String
         */
        if (Arrays.stream(BASE_CLASS_NAMES).anyMatch(v -> v.equals(t.getClass().getName()))) {
            return t.toString().getBytes(DEFAULT_CHARSET);
        } else {
            return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        if (str.startsWith("{") || str.startsWith("[")) {
            return JSON.parseObject(str, clazz);
        }
        return (T) str;


    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}

