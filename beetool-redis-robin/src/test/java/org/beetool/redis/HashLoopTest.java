package org.beetool.redis;

import lombok.extern.slf4j.Slf4j;
import org.beetool.redis.bean.Man;
import org.beetool.redis.bean.ManHashObject;
import org.beetool.redis.robin.loop.queue.model.RedisHashObjectHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author cyber.pan
 * @Classname HashLoopTest
 * @Description
 * @Date 2023/3/28 10:06
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class HashLoopTest {

    @Autowired
    private RedisConnectionFactory factory;

    @Test
    public void hashTest() {
        RedisHashObjectHelper<Long, Man> hashObjectHelper = new ManHashObject(factory);
        hashObjectHelper.add("kkkk", 1024L, new Man("cyber", 29));
        Long size = hashObjectHelper.size("kkkk");
        Man man = hashObjectHelper.getAttributeByKey("kkkk", 1024L);
        System.out.println();
    }


}
