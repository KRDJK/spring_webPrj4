package com.project.web_prj.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// 초기설정
// DB 관련 설정 클래스라고 명시
@Configuration
@ComponentScan(basePackages = "com.project.web_prj") // 어디를 기준으로 스캔할건지 명시 root를 기준으로 하면 전체가 됨.
public class DataBaseConfig {

    // DB 접속 정보 설정 (DataSource 설정) : 접속 정보 빈을 등록하는 과정
    @Bean
    public DataSource dataSource() { // 메서드 명은 중요하지 않다.

        HikariConfig config = new HikariConfig();
        // ============= Oracle ==================== //
//        config.setUsername("spring4");
//        config.setPassword("1234");
//        config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
//        config.setDriverClassName("oracle.jdbc.driver.OracleDriver");


        // ============= MariaDB ==================== //
        config.setUsername("root");
        config.setPassword("mariadb");
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/spring4");
        config.setDriverClassName("org.mariadb.jdbc.Driver");

        return new HikariDataSource(config);
    }
}