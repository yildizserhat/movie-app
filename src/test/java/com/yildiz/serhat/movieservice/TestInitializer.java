package com.yildiz.serhat.movieservice;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;

public class TestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final int MYSQL_PORT = 3306;


    static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withExposedPorts(MYSQL_PORT)
            .withUsername("user")
            .withPassword("12345")
            .withInitScript("schema.sql")
            .withDatabaseName("movies")
            .withEnv("TZ","UTC")
            .withReuse(true);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        // Start containers
        mySQLContainer.start();

        // Override mysql config
        String databaseHost = "DATABASE_URL=" + mySQLContainer.getJdbcUrl();

        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext, databaseHost);
    }
}
