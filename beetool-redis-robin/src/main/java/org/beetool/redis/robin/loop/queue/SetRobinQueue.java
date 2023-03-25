package org.beetool.redis.robin.loop.queue;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

/**
 * @author cyber.pan
 * @Classname SetRobinLoop
 * @Description
 * @Date 2023/3/24 16:52
 */
public class SetRobinQueue<T> extends RobinQueue<T> {
    /**
     * set操作工具
     */
    private SetOperations<String, T> operations;

    /**
     * 初始化
     *
     * @param name
     * @param redisTemplate
     */
    public SetRobinQueue(String name, RedisTemplate<String, T> redisTemplate) {
        super(name,redisTemplate);
        operations = redisTemplate.opsForSet();
    }


    @Override
    public Long getCurrentQueueSize() {
        return operations.size(getQueueName());
    }

    @Override
    public T pop() {
        return operations.pop(getQueueName());
    }


    Long add(T t) {
        return operations.add(getQueueName(), t);
    }

}
