package org.beetool.redis.robin.loop.queue.model.connector;

import lombok.Getter;
import org.beetool.redis.robin.loop.queue.model.connector.serializer.FastJson2JsonRedisParamSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author cyber.pan
 * @Classname RobinConnector
 * @Description \
 * @Date 2023/3/25 20:50
 */

public abstract class RobinConnector<T> {

    @Getter
    private RedisTemplate<String, T> redisTemplate;

    protected RobinConnector(RedisConnectionFactory factory) {
        this.init(factory);
    }


    void init(RedisConnectionFactory factory) {
        redisTemplate = new RedisTemplate<>();
        //把工厂设置给Template
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.setValueSerializers(new FastJson2JsonRedisParamSerializer(Object.class));
    }

    public void setValueSerializers(RedisSerializer valueSerializer) {
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.afterPropertiesSet();
    }
}
