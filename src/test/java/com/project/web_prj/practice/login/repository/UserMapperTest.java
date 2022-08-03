package com.project.web_prj.practice.login.repository;

import com.project.web_prj.practice.login.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper mapper;


    @Test
    @DisplayName("회원가입 성공해야한다.")
    void registerTest() {

        User user = new User();
        user.setId("abc123");
        user.setPassword("abc123");
        user.setName("abc성애자");
        user.setEmail("abc123@naver.com");

        boolean flag = mapper.register(user);

        assertTrue(flag);
    }
}