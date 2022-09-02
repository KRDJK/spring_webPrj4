package com.project.web_prj.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataBaseConfigTest {

    @Autowired
    DataBaseConfig config;

    @Test
    void propTest() {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataBaseConfig.class);

//        DataSource dataSource = context.getBean(DataSource.class);
//        System.out.println("dataSource = " + dataSource);

        System.out.println(config.getUsername());
        System.out.println(config.getUrl());
    }

}