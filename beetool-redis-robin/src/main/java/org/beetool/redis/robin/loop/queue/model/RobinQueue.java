package org.beetool.redis.robin.loop.queue.model;


import org.beetool.redis.robin.loop.queue.model.connector.RobinExpireThisOption;
import org.beetool.redis.robin.loop.queue.model.connector.serializer.FastJson2JsonRedisParamSerializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;

/**
 * @author cyber.pan
 * @Classname RobinLoop
 * @Description
 * @Date 2023/3/24 16:18
 */


public abstract class RobinQueue<T> extends RobinExpireThisOption<T> {

    public RobinQueue(String name, RedisConnectionFactory factory) {
        super(name, factory);
    }

    /**
     * 获取当前的size
     *
     * @return
     */
    public abstract Long getCurrentQueueSize();

    /**
     * 推出元素
     *
     * @return
     */
    public abstract T pop();

    /**
     * 脚本执行
     *
     * @param keys:key参数
     * @param path:脚本路径
     * @param args:变量参数
     * @return
     */
    public T executeScript(List<String> keys, String path, Object... args) {
        return getRedisTemplate().execute(getDefaultRedisScript(path), new FastJson2JsonRedisParamSerializer(),
                (RedisSerializer<T>) getRedisTemplate().getValueSerializer(), keys, args);
    }

    /**
     * 执行返回bool
     *
     * @param keys
     * @param path
     * @param args
     * @return
     */
    public Boolean executeBoolScript(List<String> keys, String path, Object... args) {
        return getRedisTemplate().execute(getBoolRedisScript(path), new FastJson2JsonRedisParamSerializer(),
                new FastJson2JsonRedisParamSerializer(Boolean.class), keys, args);
    }

    /**
     * 拉取脚本
     *
     * @param path
     * @return
     */
    private DefaultRedisScript<T> getDefaultRedisScript(String path) {
        DefaultRedisScript<T> redisScript = new DefaultRedisScript<T>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        redisScript.setResultType(getTClass());
        return redisScript;
    }

    /**
     * 返回的double
     *
     * @param keys
     * @param path
     * @param args
     * @return
     */
    public Double executeDoubleScript(List<String> keys, String path, Object... args) {
        return getRedisTemplate().execute(getDoubleRedisScript(path), new FastJson2JsonRedisParamSerializer(),
                new FastJson2JsonRedisParamSerializer(Double.class), keys, args);
    }

    /**
     * 返回bool的脚本
     *
     * @param path
     * @return
     */
    private DefaultRedisScript<Boolean> getBoolRedisScript(String path) {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    /**
     * 返回bool的脚本
     *
     * @param path
     * @return
     */
    private DefaultRedisScript<Double> getDoubleRedisScript(String path) {
        DefaultRedisScript<Double> redisScript = new DefaultRedisScript<Double>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        redisScript.setResultType(Double.class);
        return redisScript;
    }
}
