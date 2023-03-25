package org.beetool.redis.robin.loop.queue;


import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.*;
import org.springframework.scripting.support.ResourceScriptSource;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author cyber.pan
 * @Classname RobinLoop
 * @Description
 * @Date 2023/3/24 16:18
 */


public abstract class RobinQueue<T> {

    private RedisTemplate<String, T> redisTemplate;

    private String name;

    RobinQueue(String name, RedisTemplate<String, T> redisTemplate) {
        this.name = name;
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    }

    public String getQueueName() {
        return this.name;
    }


    public RedisTemplate<String, T> getRedisTemplate() {
        return this.redisTemplate;
    }

    /**
     * 获取当前的size
     *
     * @return
     */
    abstract Long getCurrentQueueSize();

    /**
     * 推出的
     *
     * @return
     */
    abstract T pop();

    /**
     * 获取的T
     *
     * @return
     */
    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T executeScript(List<String> keys, String path, Object... args) {
        return getRedisTemplate().execute(getDefaultRedisScript(path), new FastJson2JsonRedisParamSerializer(Object.class),
                (RedisSerializer<T>) this.redisTemplate.getValueSerializer(), keys, args);
    }

    private DefaultRedisScript<T> getDefaultRedisScript(String path) {
        DefaultRedisScript<T> redisScript = new DefaultRedisScript<T>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        redisScript.setResultType(getTClass());
        return redisScript;
    }
}
