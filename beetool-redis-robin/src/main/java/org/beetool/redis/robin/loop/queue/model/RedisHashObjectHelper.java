package org.beetool.redis.robin.loop.queue.model;

import lombok.Data;
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

    /**
     * 属性是否存在
     * @param name
     * @param attributeName
     * @return
     */
    public Boolean hasAttribute(String name,T attributeName) {
        return hashOperations.hasKey(name, attributeName);
    }

    /**
     * 删除属性值
     * @param name
     * @param attributeName
     * @return
     */
    public Long delAttribute(String name,T attributeName) {
        return hashOperations.delete(name, attributeName);
    }

    /**
     * size
     * @param name
     * @return
     */
    public Long size(String name) {
        return hashOperations.size(name);
    }

    /**
     * 添加元素
     * @param name
     * @param t
     * @param k
     */
    public void add(String name,T t, K k) {
        hashOperations.put(name, t, k);
    }

    /**
     * 添加所有元素
     * @param name
     * @param map
     */
    public void addAll(String name,Map<T, K> map) {
        hashOperations.putAll(name, map);
    }

    /**
     * 添加分数
     * @param name
     * @param t
     * @param delta
     * @return
     */
    public Long incrDeltaByKey(String name,T t, Long delta) {
        return hashOperations.increment(name, t, delta);
    }

    /**
     * 获取元素
     * @param name
     * @param t
     * @return
     */
    public K getAttributeByKey(String name,T t) {
        return hashOperations.get(name, t);
    }

    /**
     * 添加
     * @param name
     * @param t
     * @param k
     * @return
     */
    public Boolean addAttributeIfAbsent(String name,T t, K k) {
        return hashOperations.putIfAbsent(name, t, k);
    }

    /**
     * @param name
     * @param t
     * @return
     */
    public Long getAttributeLengthByKey(String name,T t) {
        return hashOperations.lengthOfValue(name, t);
    }

    /**
     * @param name
     * @param t
     * @return
     */
    public Long deleteAttributeByKey(String name,T t) {
        return hashOperations.delete(name, t);
    }
}
