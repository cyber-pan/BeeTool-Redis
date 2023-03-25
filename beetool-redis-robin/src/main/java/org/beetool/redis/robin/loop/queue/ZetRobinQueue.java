package org.beetool.redis.robin.loop.queue;

import org.springframework.data.redis.core.RedisTemplate;
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
public class ZetRobinQueue<T> extends RobinQueue<T> {


    ZSetOperations<String, T> zSetOperations;

    public ZetRobinQueue(String name, RedisTemplate<String, T> redisTemplate) {
        super(name, redisTemplate);
        zSetOperations = redisTemplate.opsForZSet();
    }

    @Override
    public Long getCurrentQueueSize() {
        return zSetOperations.size(getQueueName());
    }

    @Override
    public T pop() {
        return popMax();
    }

    public T popMax() {
        return Optional.ofNullable(zSetOperations.popMax(getQueueName())).map(ZSetOperations.TypedTuple::getValue).orElse(null);
    }

    public T popMin() {
        return Optional.ofNullable(zSetOperations.popMin(getQueueName())).map(ZSetOperations.TypedTuple::getValue).orElse(null);
    }

    public Boolean add(T t, double score) {
        return zSetOperations.add(getQueueName(), t, score);
    }

    public Double incrScore(T t, Double delta) {
        return zSetOperations.incrementScore(getQueueName(), t, delta);
    }

    public Double getScore(T t) {
        return zSetOperations.score(getQueueName(), t);
    }

    public T peekMin() {
        return Optional.ofNullable(zSetOperations.range(getQueueName(), 0, 0)).filter(v -> !v.isEmpty())
                .map(v -> v.stream().findFirst().get()).orElse(null);

    }

    public T peekMax() {
        return Optional.ofNullable(zSetOperations.reverseRange(getQueueName(), 0, 0)).filter(v -> !v.isEmpty())
                .map(v -> v.stream().findFirst().get()).orElse(null);
    }


    public T peekMinIncr() {
        return peekMinIncrDelta(1D);
    }

    public T peekMaxIncr() {
        return peekMaxIncrDelta(1D);
    }

    public T peekMaxIncrDelta(Double delta) {
        return executeScript(new ArrayList<>(Arrays.asList(getQueueName())), "redis/ZetRobinQueue_peekMaxIncrDelta.lua", delta);
    }

    public T peekMinIncrDelta(Double delta) {
        return executeScript(new ArrayList<>(Arrays.asList(getQueueName())), "redis/ZetRobinQueue_peekMinIncrDelta.lua", delta);
    }


}
