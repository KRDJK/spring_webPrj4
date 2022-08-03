package com.project.web_prj.member.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class LoginDTO {

    // 로그인할 때 클라이언트가 전송하는 데이터만 필드에 넣어라.
    private String account;
    private String password;
    private boolean autoLogin;
}
