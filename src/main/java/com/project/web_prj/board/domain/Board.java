package com.project.web_prj.board.domain;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
@EqualsAndHashCode // 이건 왜 넣을까?
@NoArgsConstructor @AllArgsConstructor
public class Board { // 보통 실무에서는 테이블명이랑 똑같이 만든다. 이 경우에는 TblBoard 로 만들면 되겠다.

    // 테이블 컬럼에 상응하는 필드들
    private Long boardNo; // wrapper로 잡으면 초기값 null // 이걸로 하는 이유는 딱히 없다.
    private String writer;
    private String title;
    private String content;
    private Long viewCnt;
    private Date regDate; // java.util에 있는걸로!
    private String account; // 22.08.04 테이블 컬럼 추가에 따른 필드 추가


    // 커스텀 데이터 필드
    private String shortTitle; // 줄임 제목
    private String prettierDate; // 변경된 날짜 포맷
    private boolean newArticle; // 신규 게시물 여부
    private int replyCount; // 댓글 수 - 22 / 07 / 28 학습


    private List<String> fileNames; // 첨부파일들의 이름 목록


    public Board(ResultSet rs) throws SQLException {
        this.boardNo = rs.getLong("board_no");
        this.writer = rs.getString("writer");
        this.title = rs.getString("title");
        this.content = rs.getString("content");
        this.viewCnt = rs.getLong("view_cnt");
        this.regDate = rs.getTimestamp("reg_date");
    }
}
