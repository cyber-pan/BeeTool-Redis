package org.beetool.redis.robin.loop;

import org.beetool.redis.robin.loop.queue.ZetRobinQueue;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author cyber.pan
 * @Classname RobinStringLoop
 * @Description
 * @Date 2023/3/24 21:06
 */
public class RobinStringLoop extends ZetRobinQueue<String> {
    public RobinStringLoop(String name, RedisTemplate<String, String> redisTemplate) {
        super(name, redisTemplate);
    }
}
