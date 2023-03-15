package com.ewok.study.springboot.web;

import com.ewok.study.springboot.service.posts.PostsService;
import com.ewok.study.springboot.web.dto.PostsResponseDto;
import com.ewok.study.springboot.web.dto.PostsSaveRequestDto;
import com.ewok.study.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}

/*
    @PutMapping
        - 멱등성. 똑같은 수정을 여러변해도 변경되지 않음. 같은 걸 여러변 호출해도 결과가 같음
        https://velog.io/@yjw8459/PostMapping%EA%B3%BC-PutMapping%EC%9D%98-%EC%B0%A8%EC%9D%B4%EC%A0%90
 */