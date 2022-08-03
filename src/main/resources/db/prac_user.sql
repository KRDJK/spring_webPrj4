-- 회원 관리 테이블
CREATE TABLE prac_user (
    account VARCHAR2(50), -- 계정명
    password VARCHAR2(150) NOT NULL, -- 평문으로 저장하면 안됨. (있는 그대로의 패스워드를 저장하면 법적으로 문제가 된다.)
                                    -- 그래서 암호화를 해야하기 때문에 바이트 수를 크게 잡았다.
                                    -- 어떤 사이트건 관리자도 비번을 모르기 때문에 비번을 까먹었으면 바꾸라고 해야 한다.
    name VARCHAR2(50) NOT NULL, -- 사용자 이름
    email VARCHAR2(100) NOT NULL UNIQUE, -- 최근에는 이메일 자체를 계정명으로 통합하기도 한다.
                                        -- JMS (자바 메일 센더) 같은걸 활용하면 이메일 인증 등을 쉽게 할 수 있다고 함.
    auth VARCHAR2(20) DEFAULT 'COMMON', -- 해당 회원의 권한이다. 기본값은 제일 낮은 단계, admin도 줄 것이다.
    reg_date DATE DEFAULT SYSDATE, -- 가입일자
    CONSTRAINT pk_prac_user PRIMARY KEY (account) -- 계정명은 고유해야 한다.
);