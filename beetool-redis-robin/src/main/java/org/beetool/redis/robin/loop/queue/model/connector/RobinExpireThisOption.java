package org.beetool.redis.robin.loop.queue.model.connector;

import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author cyber.pan
 * @Classname RobinExpireThisOption
 * @Description
 * @Date 2023/3/27 22:07
 */
public abstract class RobinExpireThisOption<T> extends RobinConnector<T> {

    /**
     * key
     */
    private String name;

    public RobinExpireThisOption(String name, RedisConnectionFactory factory) {
        super(factory);
        this.name = name;
    }

    public String getQueueName() {
        return this.name;
    }


    /**
     * 当前key是否存在
     *
     * @return
     */
    public Boolean existThis() {
        return this.existGlobalKey(getQueueName());
    }

    /**
     * 获取当前的过期时间
     *
     * @return
     */
    public Long getExpireThis() {
        return this.getExpireForGlobalKey(getQueueName());
    }

    /**
     * 设置当前的key的过期时间
     *
     * @param time
     * @param unit
     * @return
     */
    public Boolean expireThis(Long time, TimeUnit unit) {
        return this.expireGlobalKey(getQueueName(), time, unit);
    }

    /**
     * 设置过期时间点
     *
     * @param date
     * @return
     */
    public Boolean expireThisAt(Date date) {
        return expireGlobalKeyAt(getQueueName(), date);
    }

}
