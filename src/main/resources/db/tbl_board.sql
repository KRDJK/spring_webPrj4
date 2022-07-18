-- 22.07.18 게시판 첨부터 만들려고 세팅
CREATE SEQUENCE seq_tbl_board;

DROP TABLE tbl_board;
CREATE TABLE tbl_board (
    board_no NUMBER(10),
    writer VARCHAR2(20) NOT NULL,
    title VARCHAR2(200) NOT NULL,
    content CLOB, -- 대량의 텍스트를 받기 위해
    view_cnt NUMBER(10) DEFAULT 0,
    reg_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_tbl_board PRIMARY KEY (board_no)
);