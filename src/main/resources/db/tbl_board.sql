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

-- 22.09.01 Maria DB 버전
CREATE TABLE tbl_board (
    board_no INT(10) AUTO_INCREMENT, -- 시퀀스 역할을 수행한다
    writer VARCHAR(20) NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT, -- clob
    view_cnt INT(10) DEFAULT 0,
    reg_date DATETIME DEFAULT current_timestamp, -- date, sysdate가 current_timestamp
    CONSTRAINT pk_tbl_board PRIMARY KEY (board_no)
);


-- 22.07.19 게시판 paging을 하기 위해 SELECT 문을 다시 짜야 한다.
-- ROWNUM은 모든 테이블에 존재하는 가상 컬럼!! 그냥 행번호를 출력할 뿐이다. 데이터 순번일뿐.
SELECT ROWNUM, tbl_board.* -- tbl_board 하위 모든 컬럼명을 말하는 것.
FROM tbl_board
WHERE ROWNUM BETWEEN 11 AND 20 -- SELECT 절과 WHERE절의 괴리 발생.
ORDER BY board_no DESC
;



SELECT *
FROM (SELECT ROWNUM rn, v_board.* -- 2.ROWNUM(순번)과 V_board의 하위 컬럼들을 추출하기 위한 서브쿼리
        FROM (
                SELECT * -- 1. 정렬을 위한 서브 쿼리
                FROM tbl_board
                ORDER BY board_no DESC -- 1. 정렬을 미리 해놓고 거기서 추출해야 하니까
                ) v_board)
WHERE rn BETWEEN 1 AND 10
;