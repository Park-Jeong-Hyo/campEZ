package com.campEZ.Project0;

import com.campEZ.Project0.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginCheckInterceptor())
            .order(1) //실행 순서
            .addPathPatterns("/**") // /**이하의 모든 경로를 인터셉터에 포함
            .excludePathPatterns(
                    "/",      //초기화면
                    "/community/bulletinBoard", //자유게시판
                    "/community/question",  //질문게시판
                    "/community/{id}/b_read",  //조회
                    "/community/{id}/q_read",  //조회
                    "/search/**",     // 검색
                    "/signup/**",  //회원가입
                    "/login/**",  //로그인
                    "/logout/**", //로그아웃
                    "/member/**", //멤버 이하 모든 페이지
                    "/css/**",
                    "/js/**",
                    "/img/**",
                    "/error/**",
                    "/errorPage/**"
            );
  }
}
