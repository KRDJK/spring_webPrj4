package com.project.web_prj.reply.domain;

import lombok.*;

import java.util.Date;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {

    private Long replyNo; //댓글번호
    private String replyText; //댓글내용
    private String replyWriter; //댓글작성자
    private Date replyDate; //작성일자
    private Long boardNo; //원본 글번호
    private String account; // 22.08.04 테이블 컬럼 추가에 따른 필드 추가
}