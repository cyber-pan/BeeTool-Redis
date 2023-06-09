package org.beetool.redis.robin.loop.queue.model.connector.serializer;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

/**
 * 基于fastjson的序列化
 * 目前redis的序列化地方大致有四个地方：
 * 1.key的序列化；
 * 2.value的序列化；
 * 3.luna脚本函数里面参数的序列化；
 * <p>
 * 反序列化有两个地方：
 * 1.value的反序列化；
 * 2.luna脚本执行结果的序列化；
 *
 * @param <T>
 */
public class FastJson2JsonRedisParamSerializer<T> implements RedisSerializer<T> {

    private ObjectMapper objectMapper = new ObjectMapper();

    public static final Charset DEFAULT_CHARSET = Charset.forName(StandardCharsets.UTF_8.name());
    /**
     * 基础类型参数
     */
    private static final String[] BASE_CLASS_NAMES = {"java.lang.Long", "java.lang.Integer", "java.lang.Double", "java.lang.String", "java.lang.Short"};

    private Class<T> clazz;

    static {
        ParserConfig.getGlobalInstance().setAsmEnable(true);
    }

    /**
     * 没有clazz时，反序列化，反序列化是失效的
     */
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
    public byte[] serialize(T t) {
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
    public T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return Optional.ofNullable(clazz).map(c -> JSON.parseObject(str, c))
                .orElse((T) str);
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}

