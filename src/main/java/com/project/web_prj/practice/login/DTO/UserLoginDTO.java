package com.project.web_prj.practice.login.DTO;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class UserLoginDTO {

    private String id;
    private String password;
    private boolean autoLogin;

}
