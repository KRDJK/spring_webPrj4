TRUNCATE TABLE tbl_reply;


DELETE FROM tbl_board;


TRUNCATE TABLE file_upload;


commit;

SELECT * FROM file_upload;

ALTER TABLE tbl_board
ADD account VARCHAR2(50) NOT NULL;

ALTER TABLE tbl_reply
ADD account VARCHAR2(50) NOT NULL;