package com.project.web_prj.practice.login.repository;

import com.project.web_prj.practice.login.DTO.DuplicateChecker;
import com.project.web_prj.practice.login.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    boolean register(User user);

    int isDuplicate(DuplicateChecker checker);

    User findUser(String id);
}
