package com.yildiz.serhat.movieservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = TestInitializer.class)
class MovieAppApplicationTests {

    @Test
    void contextLoads() {
    }

}
