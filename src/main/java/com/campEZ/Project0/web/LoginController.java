package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.members.svc.MembersSVC;
import com.campEZ.Project0.web.form.login.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
  private final MembersSVC membersSVC;

  //로그인 화면
  @GetMapping
  public String loginForm(Model model) {
    LoginForm loginForm = new LoginForm();
    model.addAttribute("loginForm", loginForm);
    return "member/LogIn";
  }
  //로그인 처리
  @PostMapping
  public String login(
          //폼객체를 모델객체에 자동으로 추가, view에서 모델 객체에 이름으로 접근이 가능하게 해줌.
          @Valid @ModelAttribute LoginForm loginForm,
          BindingResult bindingResult,
          HttpServletRequest httpServletRequest,
          @RequestParam(required = false,defaultValue = "/") String redirectUrl
  ) {
    if(bindingResult.hasErrors()) {
      //Valid에서 유효성 검사, 오류가 있을 경우 로그인화면으로 리턴
      log.info("bindingResult={}", bindingResult);
      return "member/LogIn";
    }
    //1) 아이디 존재 유무(필드에러,rejectValue사용)
    if(!membersSVC.isExist(loginForm.getMid())) {
      bindingResult.rejectValue("mid","login", "아이디가 존재하지 않습니다.");
      return "member/LogIn";
    }
    //2) 로그인(필드 에러, rejectValue사용)
    Optional<Members> members = membersSVC.login(loginForm.getMid(), loginForm.getPasswd());
    if(members.isEmpty()) {
      bindingResult.rejectValue("passwd", "login", "비밀번호가 맞지 않습니다.");
      return "member/LogIn";
    }
    //3 세션 생성
    //getSession(true)일 경우 세션이 있으면 가져오고 없으면 생성
    HttpSession session = httpServletRequest.getSession(true);
    LoginMembers loginMembers = new LoginMembers(
            members.get().getMid(),
            members.get().getNickname(),
            members.get().getMtype()
    );
    //SessionID가 중복 사용되므로 상수로 정의함.
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMembers);
    log.info("mtype={}", members.get().getMtype());
    log.info("loginMembers={}", loginMembers);
    log.info("Session ID: {}", session.getId());
    return "redirect:"+redirectUrl;
  }
  //로그아웃
  @GetMapping("/logout")
  public String logout(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(false);
    //세션이 있으면 세션 제거
    if(session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }
}
