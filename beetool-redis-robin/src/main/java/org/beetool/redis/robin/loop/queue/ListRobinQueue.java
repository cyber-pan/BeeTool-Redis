package org.beetool.redis.robin.loop.queue;

import org.beetool.redis.robin.loop.queue.model.RobinQueue;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cyber.pan
 * @Classname ListRobinQueue
 * @Description
 * @Date 2023/3/27 17:20
 */
public abstract class ListRobinQueue<T> extends RobinQueue<T> {

    private ListOperations<String, T> listOperations;

    public ListRobinQueue(String name, RedisConnectionFactory factory) {
        super(name, factory);
        listOperations = super.getRedisTemplate().opsForList();
    }


    @Override
    public Long getCurrentQueueSize() {
        return listOperations.size(getQueueName());
    }

    @Override
    public T pop() {
        return listOperations.rightPop(getQueueName());
    }


    public List<T> range(long start, long end) {
        return listOperations.range(getQueueName(), start, end);
    }


    public void trim(long start, long end) {
        listOperations.trim(getQueueName(), start, end);
    }


    public Long leftPush(T value) {
        return listOperations.leftPush(getQueueName(), value);
    }


    public Long leftPushAll(T... values) {
        return listOperations.leftPushAll(getQueueName(), values);
    }


    public Long leftPushAll(Collection<T> values) {
        return listOperations.leftPushAll(getQueueName(), values);
    }


    public Long leftPushIfPresent(T value) {
        return listOperations.leftPushIfPresent(getQueueName(), value);
    }


    public Long leftPush(T pivot, T value) {
        return listOperations.leftPush(getQueueName(), pivot, value);
    }


    public Long rightPush(T value) {
        return listOperations.rightPush(getQueueName(), value);
    }


    public Long rightPushAll(T... values) {
        return listOperations.rightPushAll(getQueueName(), values);
    }


    public Long rightPushAll(Collection<T> values) {
        return listOperations.rightPushAll(getQueueName(), values);
    }

    public Long rightPushIfPresent(T value) {
        return listOperations.rightPushIfPresent(getQueueName(), value);
    }

    public Long rightPush(T pivot, T value) {
        return listOperations.rightPush(getQueueName(), pivot, value);
    }


    public void set(long index, T value) {
        listOperations.set(getQueueName(),index, value);
    }


    public Long remove(long count, Object value) {
        return listOperations.remove(getQueueName(),count,value);
    }


    public T index(long index) {
        return listOperations.index(getQueueName(),index);
    }


    public Long indexOf(T value) {
        return listOperations.indexOf(getQueueName(),value);
    }


    public Long lastIndexOf(T value) {
        return listOperations.lastIndexOf(getQueueName(),value);
    }


    public T leftPop() {
        return listOperations.leftPop(getQueueName());
    }


    public List<T> leftPop(long count) {
        return listOperations.leftPop(getQueueName(),count);
    }


    public T leftPop(long timeout, TimeUnit unit) {
        return listOperations.leftPop(getQueueName(),timeout,unit);
    }


    public T rightPop() {
        return listOperations.rightPop(getQueueName());
    }


    public List<T> rightPop(long count) {
          return listOperations.rightPop(getQueueName(),count);
    }


    public T rightPop(long timeout, TimeUnit unit) {
        return listOperations.rightPop(getQueueName(),timeout,unit);
    }

}
