package com.ewok.study.springboot.config.auth;

import com.ewok.study.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정들을 활성화시켜 준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {  // 방식 변경됨

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()    // h2-console 화면을 사용하기 위해 해당 옵션들을 disable한다.
                .and()
                .authorizeRequests()    // URL별 권한 관리를 설정하는 옵션 시작점. authorizeRequests가 선언되어야먄 antMatchers 옵션 사용 가능
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);

    }
}

/*
    WebSecurityConfigurerAdapter는 deprecated가 되어, 상속을 받지 않고 모두 Bean으로 등록하여 사용하는 방식으로 변경되었다.

    @RequiredArgsConstructor
    @EnableWebSecurity
    public class SecurityConfig {

        private final CustomOAuth2UserService customOAuth2UserService;

        @Bean
        public SecurityFilterChain newFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService));

        return http.build();
    }
    }
 */

/*
    antMatchers
        - 권한 관리 대상을 지정하는 옵션
        - URL, HTTP 메소드별로 관리 가능
        - "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 줌
        - "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 함

    anyRequest
        - 설정된 값들 이외 나머지 URL들을 나타낸다.
        - 여기서는 authenticated()을 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용
        - 인증된 사용자 즉, 로그인ㅇ한 사용자들을 말함

    logout().logoutSuccessUrl("/")
        - 로그아웃 기능에 대한 여러 설정의 진입점
        - 로그아웃 성공 시 / 주소로 이동

    oauth2Login
        - OAuth 2 로그인 기능에 대한 여러 설정의 진입점

    userInfoEndpoint
        - OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당

    userService
        - 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
        - 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 가능
 */