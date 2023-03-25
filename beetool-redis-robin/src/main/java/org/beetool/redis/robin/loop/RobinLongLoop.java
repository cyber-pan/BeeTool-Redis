package org.beetool.redis.robin.loop;

import org.beetool.redis.robin.loop.queue.ZetRobinQueue;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author cyber.pan
 * @Classname RobinLongLoop
 * @Description
 * @Date 2023/3/24 20:51
 */
public class RobinLongLoop extends ZetRobinQueue<Long> {
    public RobinLongLoop(String name, RedisConnectionFactory factory) {
        super(name, factory);
    }
}
