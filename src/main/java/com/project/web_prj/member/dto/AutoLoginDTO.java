package com.project.web_prj.member.dto;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class AutoLoginDTO {

    private String account;
    private String sessionId;
    private Date limitTime;
}
