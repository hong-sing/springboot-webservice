package com.ewok.study.springboot.service.posts;

import com.ewok.study.springboot.domain.posts.Posts;
import com.ewok.study.springboot.domain.posts.PostsRepository;
import com.ewok.study.springboot.web.dto.PostsListResponseDto;
import com.ewok.study.springboot.web.dto.PostsResponseDto;
import com.ewok.study.springboot.web.dto.PostsSaveRequestDto;
import com.ewok.study.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // readOnly = true 트랙잭션 범위는 유지하되, 조회기능만 남겨 조회 속도 개선. / 등록, 수정, 삭제 기능이 없는 서비스 메소드에서 사용 추천
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}

/*
    - update기능에서 쿼리를 날리는 부분이 없다. JPA의 영속성 컨텍스트 때문이다.
    - 영속성 컨텍스트는 엔티티를 영구 저장하는 환경이다.
    - JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈림
    - JPA의 엔티티매니저가 활성화된 상태로(Spring Data Jpa를 쓴다면 기본 옵션임) 트랜잭션 안에서
      데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태
    - 이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영
    - 즉, 엔티티 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없음. 이를 더티 체킹이라고 함
    https://jojoldu.tistory.com/415
 */