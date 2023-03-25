package org.beetool.redis.robin.loop.queue.model;


import org.beetool.redis.robin.loop.queue.model.connector.RobinConnector;
import org.beetool.redis.robin.loop.queue.model.connector.serializer.FastJson2JsonRedisParamSerializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author cyber.pan
 * @Classname RobinLoop
 * @Description
 * @Date 2023/3/24 16:18
 */


public abstract class RobinQueue<T> extends RobinConnector<T> {


    private String name;

    public RobinQueue(String name, RedisConnectionFactory factory) {
        super(factory);
        this.name = name;
    }

    public String getQueueName() {
        return this.name;
    }


    public RedisTemplate<String, T> getRedisTemplate() {
        return super.getRedisTemplate();
    }

    /**
     * 获取当前的size
     *
     * @return
     */
    public abstract Long getCurrentQueueSize();

    /**
     * 推出的
     *
     * @return
     */
    public abstract T pop();


    public T executeScript(List<String> keys, String path, Object... args) {
        return getRedisTemplate().execute(getDefaultRedisScript(path), new FastJson2JsonRedisParamSerializer(),
                (RedisSerializer<T>) super.getRedisTemplate().getValueSerializer(), keys, args);
    }

    private DefaultRedisScript<T> getDefaultRedisScript(String path) {
        DefaultRedisScript<T> redisScript = new DefaultRedisScript<T>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        redisScript.setResultType(getTClass());
        return redisScript;
    }
}
