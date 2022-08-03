package com.project.web_prj.member.domain;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
// @DATA를 쓰지 마라   유지, 보수가 불편해질 수 있다.
public class Member {

    private String account;
    private String password;
    private String name;
    private String email;
    private Auth auth;
    private Date regDate; // 이건 사실상 공통 필드라고 볼 수 있다.
                        // 어떤 동작이 일어날 때마다 그 동작이 일어난 시간을 대부분 기록하기 때문이다.
                        // 그러면 매번 쓰기보다 상속 처리를 하면 편하겠지..?
}
