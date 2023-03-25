package org.beetool.redis.robin.loop;

import org.beetool.redis.robin.loop.queue.ZetRobinQueue;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author cyber.pan
 * @Classname RobinIntegerLoop
 * @Description
 * @Date 2023/3/24 21:08
 */
public class RobinIntegerLoop extends ZetRobinQueue<Integer> {
    public RobinIntegerLoop(String name, RedisConnectionFactory factory) {
        super(name, factory);
    }
}
