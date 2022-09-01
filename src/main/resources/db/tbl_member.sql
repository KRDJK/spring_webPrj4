-- 회원 관리 테이블
CREATE TABLE tbl_member (
    account VARCHAR2(50), -- 계정명
    password VARCHAR2(150) NOT NULL, -- 평문으로 저장하면 안됨. (있는 그대로의 패스워드를 저장하면 법적으로 문제가 된다.)
                                    -- 그래서 암호화를 해야하기 때문에 바이트 수를 크게 잡았다.
                                    -- 어떤 사이트건 관리자도 비번을 모르기 때문에 비번을 까먹었으면 바꾸라고 해야 한다.
    name VARCHAR2(50) NOT NULL, -- 사용자 이름
    email VARCHAR2(100) NOT NULL UNIQUE, -- 최근에는 이메일 자체를 계정명으로 통합하기도 한다.
                                        -- JMS (자바 메일 센더) 같은걸 활용하면 이메일 인증 등을 쉽게 할 수 있다고 함.
    auth VARCHAR2(20) DEFAULT 'COMMON', -- 해당 회원의 권한이다. 기본값은 제일 낮은 단계, admin도 줄 것이다.
    reg_date DATE DEFAULT SYSDATE, -- 가입일자
    CONSTRAINT pk_member PRIMARY KEY (account) -- 계정명은 고유해야 한다.
);


-- 22.09.01 Maria DB 버전
CREATE TABLE tbl_member (
    account VARCHAR(50),
    password VARCHAR(150) NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    auth VARCHAR(20) DEFAULT 'COMMON',
    reg_date DATETIME DEFAULT current_timestamp,
    CONSTRAINT pk_member PRIMARY KEY (account)
);

ALTER TABLE tbl_board ADD account VARCHAR(50) NOT NULL;
ALTER TABLE tbl_reply ADD account VARCHAR(50) NOT NULL;

SELECT * FROM tbl_member;

ALTER TABLE tbl_member ADD session_id VARCHAR(200) DEFAULT 'none';
ALTER TABLE tbl_member ADD limit_time DATETIME;
-- end maira db


-- 실무적인 관점에서는 로그인 이력 테이블을 따로 만들어 기록을 쌓아야 한다. 회원 하나당 로그인 이력이 여러 개~~

SELECT * FROM tbl_member;