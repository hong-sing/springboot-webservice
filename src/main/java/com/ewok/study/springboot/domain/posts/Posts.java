package com.ewok.study.springboot.domain.posts;

import com.ewok.study.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 주요 어노테이션을 클래스에 가깝게
@Getter // 롬복
@NoArgsConstructor  // 롬복. 기본생성자 자동 추가. public Posts(){}와 같은 효과
@Entity // JPA 어노테이션. 테이블과 링크될 클래스. 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭. 예) SalesManager.java->sales_manager table
public class Posts extends BaseTimeEntity {    // 실제 DB의 테이블과 매칭될 클래스. Entity 클래스

    @Id // 해당 테이블의 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성규칙. GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 된다.
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder    // 해당 클래스의 빌더 패턴 클래스 생성. 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

/*
    @Column
        - 테이블의 칼럼. 굳이 선언하지 않아도 클래스의 필드는 모두 칼럼이 됨
        - 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
        - 문자열의 경우 VARCHAR(255)rk 기본값인데, 사이즈를 500으로 늘리고 싶거나,
          타입을 TEXT로 변경하고 싶거나 등의 경우에 사용

    Entity 클래스에서는 Setter 메소드를 만들지 않음
    대신, 해당 필드의 값 변경이 필요하면 멱확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가
 */