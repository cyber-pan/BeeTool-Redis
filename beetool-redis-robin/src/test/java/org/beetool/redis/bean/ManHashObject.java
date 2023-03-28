package org.beetool.redis.bean;

import org.beetool.redis.robin.loop.queue.model.RedisHashObjectHelper;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author cyber.pan
 * @Classname ManHashObject
 * @Description TODO
 * @Date 2023/3/28 10:09
 */
public class ManHashObject  extends RedisHashObjectHelper<Long,Man> {
    public ManHashObject(RedisConnectionFactory factory) {
        super(factory);
    }
}
