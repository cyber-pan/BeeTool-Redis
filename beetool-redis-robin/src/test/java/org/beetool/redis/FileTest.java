package org.beetool.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author cyber.pan
 * @Classname FileTest
 * @Description
 * @Date 2023/3/28 12:17
 */
@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = App.class)
public class FileTest {

    @Test
    public void fileTest() throws IOException {
        //ClassPathResource classPathResource = new ClassPathResource("redis/ZetRobinQueue_peekMaxIncrDelta.lua");
        ClassPathResource classPathResource2 = new ClassPathResource("application.properties");

       //classPathResource.getInputStream();
        Boolean bool2 = classPathResource2.exists();
        System.out.println();
    }

}
