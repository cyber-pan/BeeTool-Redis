package org.beetool.redis;

import lombok.extern.slf4j.Slf4j;
import org.beetool.redis.robin.loop.queue.model.connector.serializer.FastJson2JsonRedisParamSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author cyber.pan
 * @Classname FileTest
 * @Description
 * @Date 2023/3/28 12:17
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class FileTest {

    @Autowired
    private RedisConnectionFactory factory;

    @Test
    public void fileTest() throws IOException {
        //ClassPathResource classPathResource = new ClassPathResource("redis/ZetRobinQueue_peekMaxIncrDelta.lua");
        ClassPathResource classPathResource2 = new ClassPathResource("application.properties");

       //classPathResource.getInputStream();
        Boolean bool2 = classPathResource2.exists();
        System.out.println();
    }

    @Test
    public void TestRedis(){
        RedisTemplate  redisTemplate = new RedisTemplate<>();
        //把工厂设置给Template
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new FastJson2JsonRedisParamSerializer<>());
        redisTemplate.afterPropertiesSet();
        //redisTemplate.o("2323",3000, TimeUnit.SECONDS);
        Boolean bool = redisTemplate.hasKey("2323");
        System.out.println();
    }


}
