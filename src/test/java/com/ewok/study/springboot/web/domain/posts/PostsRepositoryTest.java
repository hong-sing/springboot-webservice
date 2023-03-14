package com.ewok.study.springboot.web.domain.posts;

import com.ewok.study.springboot.domain.posts.Posts;
import com.ewok.study.springboot.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest // 별다른 설정없이 @SpringBootTest를 사용하면 H2 데이터베이스를 자동으로 실행
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트게시글";
        String content = "테스트본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("ewoking777@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}

/*
    @AfterEach
        - Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
        - 보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용
        - 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어
          다음 테스트 실행 시 테스트가 실패할 수 있다.

    @postsRepository.save
        - 테이블 posts에 insert/update 쿼리를 실행한다
        - id값이 있다면 update가, 없다면 insert쿼리가 실행된다.

    @postsRepository.findAll
        - 테이블 posts에 있는 모든 데이터를 조회해오는 메소드
 */