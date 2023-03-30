package org.beetool.redis.bean;

import org.beetool.redis.robin.loop.queue.ListRobinQueue;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author cyber.pan
 * @Classname ListManQueue
 * @Description TODO
 * @Date 2023/3/30 11:23
 */
public class ListManQueue extends ListRobinQueue<Man> {
    public ListManQueue(String name, RedisConnectionFactory factory) {
        super(name, factory);
    }
}
