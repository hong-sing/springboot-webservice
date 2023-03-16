package com.ewok.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @SpringBootApplication 스프링부트의 자동설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정
//@EnableJpaAuditing  // JPA Auditing 활성화
@SpringBootApplication // @SpringBootApplication이 있는 위치부터 설정을 읽기 때문에 항상 프로젝트의 최상단에 위치 해야함
public class Application {  // 프로젝트의 메인 클래스
    public static void main(String[] args) {
        // 내장 WAS를 실행
        SpringApplication.run(Application.class, args);
    }
}

/*
    JPA Auditing
    여기서는 db 삽입, 갱신 전 날짜 데이터를 등록, 수정
 */