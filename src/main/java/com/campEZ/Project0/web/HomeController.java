package com.campEZ.Project0.web;

import com.campEZ.Project0.members.svc.MembersSVC;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

  private final MembersSVC membersSVC;
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

  // 마이페이지 맵핑
  @GetMapping("/mypage")
  public String mypage(
      HttpSession session,
      RedirectAttributes redirectAttributes
  ) {
    LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);
    String type = String.valueOf(loginMembers.getMtype());
    log.info("type={}",type);
    redirectAttributes.addAttribute("mid",loginMembers.getMid());
    log.info("redirectAttributes={}",redirectAttributes);
    if ( loginMembers.getMid() != null ) {
      if ( type.equals("b")) {
        System.out.println("b 타입");
        return "redirect:/mypage/{mid}/manager";
      } else {
        System.out.println("n 타입");
        return "redirect:/mypage/{mid}/common";
      }
    } else {
      return "mainPage/mainPage";
    }
  }

  // 준비중페이지 맵핑
  @GetMapping("/preparing")
  public String error() {
    return "errorPage/preparing";
  }
}

// 로그인페이지 맵핑
//  @GetMapping("/login")
//  public String login() {
//    return "member/logIn";
//  }

//  // 검색페이지 맵핑
//  @GetMapping("/search")
//  public String search() {
//    return "search/searchListMain";
//  }

