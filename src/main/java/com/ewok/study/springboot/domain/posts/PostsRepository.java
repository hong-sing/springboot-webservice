package com.ewok.study.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
}

/*
    Dao라고 불리는 DB Layer
    JPA에선 Repository라고 부르며 인터페이스로 생성
    인터페이스 생성 후 JpaRepository<Entity 클래스, PK타입>를 상속하면 기본적인 CRUD메소드가 자동으로 생성
    @Repository를 추가할 필요 없음
    Entity클래스와 Entity Repository는 함께 위치 해야 함
    Entity클래스는 기본 Repository없이는 제대로 역할을 할 수 없음
 */