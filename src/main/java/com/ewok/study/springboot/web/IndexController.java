package com.ewok.study.springboot.web;

import com.ewok.study.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {  // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
        model.addAttribute("posts", postsService.findAllDesc());    // postsService.findAllDesc()로 가져온 결과를 posts로
        return "index"; // index.mustache로 전달
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

}

/*
    머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됨
    실제 : src/main/resources/templates/index.mustache로 전환되어 View Resolver가 처리
 */