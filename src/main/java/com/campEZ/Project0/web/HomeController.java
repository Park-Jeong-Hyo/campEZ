package com.campEZ.Project0.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

  // 메인페이지 맵핑
  @GetMapping
  public String Home() {
    return "mainPage/mainPage";
  }

  // 회원가입페이지 맵핑
  @GetMapping("/signup")
  public String signUp() {
    return "member/SignUp";
  }

  // 로그인페이지 맵핑
  @GetMapping("/login")
  public String login() {
    return "member/logIn";
  }

  // 검색페이지 맵핑
  @GetMapping("/search")
  public String search() {
    return "search/searchListMain";
  }

  // 마이페이지 맵핑
  @GetMapping("/mypage")
  public String mypage() {
    return "myPage/myPage__common";
  }
  // 공지사항 맵핑
  @GetMapping("/notice")
  public String notice() {
    return "community/notice";
  }

  // 자유게시판 맵핑
  @GetMapping("/butlletinBoard")
  public String butlletinBoard() {
    return "community/butlletinBoard";
  }

  // 질문게시판 맵핑
  @GetMapping("/question")
  public String question() {
    return "community/question";
  }

  // 준비중페이지 맵핑
  @GetMapping("/preparing")
  public String error() {
    return "errorPage/preparing";
  }
}
