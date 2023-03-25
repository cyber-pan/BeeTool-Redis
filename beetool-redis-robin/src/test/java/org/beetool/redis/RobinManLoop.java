package org.beetool.redis;

import org.beetool.redis.robin.loop.queue.ZetRobinQueue;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author cyber.pan
 * @Classname RobinManLoop
 * @Description
 * @Date 2023/3/25 22:11
 */
public class RobinManLoop extends ZetRobinQueue<Man> {
    public RobinManLoop(String name, RedisConnectionFactory factory) {
        super(name, factory);
    }
}
