package org.beetool.redis.robin.loop.queue.model;

import lombok.Getter;
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
public class RedisHashObject<T, K> extends RobinConnector<T> {

    @Getter
    private String name;

    private HashOperations<String, T, K> hashOperations;


    public RedisHashObject(String name, RedisConnectionFactory factory) {
        super(factory);
        this.name = name;
        this.hashOperations = super.getRedisTemplate().opsForHash();
    }

    public Boolean hasAttribute(T attributeName) {
        return hashOperations.hasKey(getName(), attributeName);
    }

    public Long delAttribute(T attributeName) {
        return hashOperations.delete(getName(), attributeName);
    }

    public Long size() {
        return hashOperations.size(getName());
    }

    public void add(T t, K k) {
        hashOperations.put(getName(), t, k);
    }

    public void addAll(Map<T, K> map) {
        hashOperations.putAll(getName(), map);
    }

    public Long incrDeltaByKey(T t, Long delta) {
        return hashOperations.increment(getName(), t, delta);
    }

    public K getAttributeByKey(T t) {
        return hashOperations.get(getName(), t);
    }

    public Boolean addAttributeIfAbsent(T t, K k) {
        return hashOperations.putIfAbsent(getName(), t, k);
    }

    public Long getLengthByKey(T t) {
        return hashOperations.lengthOfValue(getName(), t);
    }

    public Long deleteByKey(T t) {
        return hashOperations.delete(getName(), t);
    }

}
