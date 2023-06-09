package org.beetool.redis.robin.loop;

import org.beetool.redis.robin.loop.queue.ZetRobinQueue;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author cyber.pan
 * @Classname RobinStringLoop
 * @Description
 * @Date 2023/3/24 21:06
 */
public class RobinStringLoop extends ZetRobinQueue<String> {
    public RobinStringLoop(String name, RedisConnectionFactory factory) {
        super(name, factory);
    }
}
