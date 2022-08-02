-- 첨부파일 정보를 가지는 테이블 생성
CREATE TABLE file_upload (
    file_name VARCHAR2(150), -- /2022/08/02/adfadsffsfssaf_상어.jpg 이런 식으로 저장할 것이다. 파일을 식별할 수 있는 경로 저장!
    -- origin_name 원본 파일명
    -- file_size 파일 크기
    -- extension 확장자
    -- expire_date 저장 만료 기간
    -- !! 실무에서 이런 세가지 외에도 메타 데이터들을 넣어주는게 유용할 때가 많다!
    reg_date DATE DEFAULT SYSDATE,
    bno NUMBER(10) NOT NULL
);

-- PK, FK 부여
ALTER TABLE file_upload
ADD CONSTRAINT pk_file_name
PRIMARY KEY (file_name);

ALTER TABLE file_upload
ADD CONSTRAINT fk_file_upload
FOREIGN KEY (bno)
REFERENCES tbl_board (board_no)
ON DELETE CASCADE;
