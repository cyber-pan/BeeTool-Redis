package org.beetool.redis.robin.loop.queue.model;

import org.beetool.redis.robin.loop.queue.model.connector.RobinConnector;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;

import java.util.Map;

/**
 * @author cyber.pan
 * @Classname RedisHashObject
 * @Description
 * @Date 2023/3/24 17:50
 */
public abstract class RedisHashObjectHelper<T, K> extends RobinConnector<T> {


    private HashOperations<String, T, K> hashOperations;


    public RedisHashObjectHelper(RedisConnectionFactory factory) {
        super(factory);
        this.hashOperations = super.getRedisTemplate().opsForHash();
    }

    public Boolean hasAttribute(String name,T attributeName) {
        return hashOperations.hasKey(name, attributeName);
    }

    public Long delAttribute(String name,T attributeName) {
        return hashOperations.delete(name, attributeName);
    }

    public Long size(String name) {
        return hashOperations.size(name);
    }

    public void add(String name,T t, K k) {
        hashOperations.put(name, t, k);
    }

    public void addAll(String name,Map<T, K> map) {
        hashOperations.putAll(name, map);
    }

    public Long incrDeltaByKey(String name,T t, Long delta) {
        return hashOperations.increment(name, t, delta);
    }

    public K getAttributeByKey(String name,T t) {
        return hashOperations.get(name, t);
    }

    public Boolean addAttributeIfAbsent(String name,T t, K k) {
        return hashOperations.putIfAbsent(name, t, k);
    }

    public Long getLengthByKey(String name,T t) {
        return hashOperations.lengthOfValue(name, t);
    }

    public Long deleteByKey(String name,T t) {
        return hashOperations.delete(name, t);
    }
}
