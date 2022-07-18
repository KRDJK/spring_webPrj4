package com.project.web_prj.board.domain;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Getter @Setter @ToString
@EqualsAndHashCode // 이건 왜 넣을까?
@NoArgsConstructor @AllArgsConstructor
public class Board { // 보통 실무에서는 테이블명이랑 똑같이 만든다. 이 경우에는 TblBoard 로 만들면 되겠다.

    private Long boardNo; // wrapper로 잡으면 초기값 null // 이걸로 하는 이유는 딱히 없다.
    private String writer;
    private String title;
    private String content;
    private Long viewCnt;
    private Date regDate; // java.util에 있는걸로!

    public Board(ResultSet rs) throws SQLException {
        this.boardNo = rs.getLong("board_no");
        this.writer = rs.getString("writer");
        this.title = rs.getString("title");
        this.content = rs.getString("content");
        this.viewCnt = rs.getLong("view_cnt");
        this.regDate = rs.getDate("reg_date");
    }
}
