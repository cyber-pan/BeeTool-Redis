package org.beetool.redis;

import lombok.extern.slf4j.Slf4j;
import org.beetool.redis.robin.loop.RobinLongLoop;
import org.beetool.redis.robin.loop.RobinStringLoop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest {
    @Autowired
    private RedisConnectionFactory factory;

    @Test
    public void shouldAnswerWithTrue() {
        RobinLongLoop robinLongLoop = new RobinLongLoop("name", factory);
        robinLongLoop.add(1L, 2);
        robinLongLoop.incrScore(1L, 1D);
        Double score = robinLongLoop.getScore(1L);
        robinLongLoop.add(1L, 1);
        Long ley = robinLongLoop.pop();


        System.out.println();

    }


    @Test
    public void zsetTest() {
        RobinStringLoop robinStringLoop = new RobinStringLoop("ruby1", factory);

        //robinLongLoop.add(2L, 2D);
        //robinLongLoop.add(3L, 3D);
        //Long k = robinLongLoop.peekMax();
        robinStringLoop.add("chen", 80D);
        Double score = robinStringLoop.getScore("chen");
        String result = robinStringLoop.peekMaxIncrDelta(1.0D);

        result = robinStringLoop.peekMax();
        System.out.println();


    }


}
