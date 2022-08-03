package com.project.web_prj.practice.login.domain;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class User {

    private String id;
    private String password;
    private String name;
    private String email;
    private A auth;
    private Date regDate;

}
