package org.beetool.redis.robin.loop.queue;

import org.beetool.redis.robin.loop.queue.model.RobinQueue;
import org.beetool.redis.robin.loop.queue.model.connector.serializer.FastJson2JsonRedisParamSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author cyber.pan
 * @Classname ZetRobinLoop
 * @Description
 * @Date 2023/3/24 17:24
 */
public abstract class ZetRobinQueue<T> extends RobinQueue<T> {


    ZSetOperations<String, T> zSetOperations;

    public ZetRobinQueue(String name, RedisConnectionFactory factory) {
        super(name, factory);
        this.zSetOperations = super.getRedisTemplate().opsForZSet();
    }

    @Override
    public Long getCurrentQueueSize() {
        return zSetOperations.size(getQueueName());
    }

    @Override
    public T pop() {
        return popMax();
    }

    /**
     * 推出分值最大的元素
     *
     * @return
     */
    public T popMax() {
        return Optional.ofNullable(zSetOperations.popMax(getQueueName())).map(ZSetOperations.TypedTuple::getValue).orElse(null);
    }

    /**
     * 推出分值最小的元素
     *
     * @return
     */
    public T popMin() {
        return Optional.ofNullable(zSetOperations.popMin(getQueueName())).map(ZSetOperations.TypedTuple::getValue).orElse(null);
    }

    /**
     * 添加元素
     *
     * @param t        :元素
     * @param score:分数
     * @return
     */
    public Boolean add(T t, Double score) {
        return zSetOperations.add(getQueueName(), t, score);
    }

    /**
     * 原子自增
     *
     * @param t
     * @param delta
     * @return
     */
    public Double incrScore(T t, Double delta) {
        return zSetOperations.incrementScore(getQueueName(), t, delta);
    }

    /**
     * 查询元素分数
     *
     * @param t
     * @return
     */
    public Double getScore(T t) {
        return zSetOperations.score(getQueueName(), t);
    }

    /**
     * 查询最小元素:不推出
     *
     * @return
     */
    public T peekMin() {
        return Optional.ofNullable(zSetOperations.range(getQueueName(), 0, 0)).filter(v -> !v.isEmpty())
                .map(v -> v.stream().findFirst().get()).orElse(null);

    }

    /**
     * 查询最大元素:不推出
     *
     * @return
     */
    public T peekMax() {
        return Optional.ofNullable(zSetOperations.reverseRange(getQueueName(), 0, 0)).filter(v -> !v.isEmpty())
                .map(v -> v.stream().findFirst().get()).orElse(null);
    }

    /**
     * 查询最小元素并自增1
     *
     * @return
     */
    public T peekMinIncr() {
        return peekMinIncrDelta(1D);
    }

    /**
     * 查询最小元素并自增1
     *
     * @return
     */
    public T peekMaxIncr() {
        return peekMaxIncrDelta(1D);
    }

    /**
     * 查询最大元素并自增delta
     *
     * @return
     */
    public T peekMaxIncrDelta(Double delta) {
        return executeScript(new ArrayList<>(Arrays.asList(getQueueName())), "redis/ZetRobinQueue_peekMaxIncrDelta.lua", delta);
    }

    /**
     * 查询最小元素并自增delta
     *
     * @return
     */
    public T peekMinIncrDelta(Double delta) {
        return executeScript(new ArrayList<>(Arrays.asList(getQueueName())), "redis/ZetRobinQueue_peekMinIncrDelta.lua", delta);
    }

    /**
     * 加(减)delta之后数在[lowLimit,highLimit],否则失败
     * @param t
     * @param delta
     * @param lowLimit  最低限制
     * @param highLimit 最高限制
     * @return
     */
    public Double incrScoreWithLimitScore(T t, Double delta, Double lowLimit,Double highLimit) {
        FastJson2JsonRedisParamSerializer<T> serializer = new FastJson2JsonRedisParamSerializer<>(getTClass());
        return executeDoubleScript(new ArrayList<>(Arrays.asList(getQueueName())), "redis/ZetRobinQueue_addScoreWithLimitScore.lua", new String(serializer.serialize(t)), delta, lowLimit,highLimit);
    }


}
