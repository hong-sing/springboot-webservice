package com.ewok.study.springboot.web;

import com.ewok.study.springboot.config.auth.dto.SessionUser;
import com.ewok.study.springboot.service.posts.PostsService;
import com.ewok.study.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {  // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
        model.addAttribute("posts", postsService.findAllDesc());    // postsService.findAllDesc()로 가져온 결과를 posts로
        SessionUser user = (SessionUser) httpSession.getAttribute("user");  // 1
        if (user != null) { // 2
            model.addAttribute("userName", user.getName());
        }
        return "index"; // index.mustache로 전달
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

}

/*
    머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됨
    실제 : src/main/resources/templates/index.mustache로 전환되어 View Resolver가 처리

    1. (SessionUser) httpSession.getAttribute("user")
        - 앞서 작성된 CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성했었다.
        - 즉, 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있음

    2. if (user != null)
        - 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다.
        - 세션에 저장된 값이 없음녀 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게 된다.
 */