package org.beetool.redis.robin.loop.queue.model.connector;

import lombok.Getter;
import org.beetool.redis.robin.loop.queue.model.connector.serializer.FastJson2JsonRedisParamSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author cyber.pan
 * @Classname RobinConnector
 * @Description
 * @Date 2023/3/25 20:50
 * @since 1.0
 * redis连接器，这个地方redisTemplate的配置由核心类把控，不开放給外部
 * 原因：redis的各种序列化的方式很多，key，value，以及脚本里面的argv也
 * 需要序列化，有一个地方有问题的话，有一致性风险，此处redis的key用StringRedisSerializer，
 * value，以及脚本里面的arg用自己提供的FastJson2JsonRedisParamSerializer：基于fastjson
 * 的序列化
 */

public abstract class RobinConnector<T> {

    private RedisTemplate<String, T> redisTemplate;

    protected RobinConnector(RedisConnectionFactory factory) {
        this.init(factory);
    }


    public RedisTemplate<String, T> getRedisTemplate() {
        return redisTemplate;
    }

    void init(RedisConnectionFactory factory) {
        redisTemplate = new RedisTemplate<>();
        //把工厂设置给Template
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new FastJson2JsonRedisParamSerializer<T>(getTClass()));
    }
    public Boolean existGlobalKey(String globalKey) {
        return redisTemplate.hasKey(globalKey);
    }

    public Long getExpireForGlobalKey(String globalKey) {
        return redisTemplate.getExpire(globalKey);
    }


    public Boolean expireGlobalKey(String globalKey, Long time, TimeUnit unit) {
        return redisTemplate.expire(globalKey, time, unit);
    }


    public Boolean expireGlobalKeyAt(String globalKey, Date date) {
        return redisTemplate.expireAt(globalKey, date);
    }
    /**
     * 获取T的class
     *
     * @return
     */
    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
