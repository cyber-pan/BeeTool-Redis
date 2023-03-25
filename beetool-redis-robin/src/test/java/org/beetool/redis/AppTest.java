package org.beetool.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.beetool.redis.robin.loop.RobinIntegerLoop;
import org.beetool.redis.robin.loop.RobinLongLoop;
import org.beetool.redis.robin.loop.RobinStringLoop;
import org.beetool.redis.robin.loop.queue.ZetRobinQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
        robinLongLoop.add(1L, 2D);
        robinLongLoop.incrScore(1L, 1D);
        Double score = robinLongLoop.getScore(1L);
        robinLongLoop.add(1L, 1D);
        Long ley = robinLongLoop.pop();
        System.out.println();

    }


    @Test
    public void StingLoopTest() {
        RobinStringLoop loop = new RobinStringLoop("ruby1", factory);
        loop.add("chen", 80D);
        Double score = loop.getScore("chen");
        String result = loop.peekMaxIncrDelta(1.0D);
        result = loop.peekMax();
        System.out.println();
    }


    @Test
    public void LongLoopTest() {
        RobinLongLoop loop = new RobinLongLoop("long", factory);
        loop.add(999L, 80D);
        Long result = loop.peekMax();
        result = loop.peekMaxIncrDelta(1.0D);
        result = loop.peekMax();
        System.out.println();
    }


    @Test
    public void integerLoopTest() {
        RobinIntegerLoop loop = new RobinIntegerLoop("integer", factory);
        loop.add(999, 80D);
        Integer result = loop.peekMax();
        result = loop.peekMaxIncrDelta(1.0D);
        Double score = loop.addScoreWithLimitScore(999,3.0D,90.0D);
         score = loop.addScoreWithLimitScore(999,89.0D,90.0D);


        result = loop.peekMax();
        System.out.println();
    }


    @Test
    public void ManLoopTest() {
        Man man = new Man("cyber", 29);
        RobinManLoop loop = new RobinManLoop("man", factory);
        //loop.add(man, 80D);
        Man result = loop.peekMax();
        result = loop.peekMaxIncrDelta(1.0D);
        result = loop.peekMax();
        System.out.println();
    }




}
