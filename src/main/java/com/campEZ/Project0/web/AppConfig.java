package com.campEZ.Project0.web;

import com.campEZ.Project0.web.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
                    "/community/notice",  //공지사항
                    "/search",     // 검색
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
