package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.members.svc.MembersSVC;
import com.campEZ.Project0.web.form.members.JoinForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MembersSVC membersSVC;

  //회원가입양식(일반)
  @GetMapping("/signup/gen")
  public String GenJoinForm(
      Model model,
      HttpSession session
  ){

    String sessionId = session.getId();
    log.info("sessionId={}",sessionId);
    JoinForm joinForm = new JoinForm();
    model.addAttribute("joinForm", joinForm);
    log.info("joinForm={}",joinForm);
    return "member/SignUpUserGen";
  }

  //회원가입처리(일반)
  @PostMapping("/signup/gen")
  public String GenJoin(@Valid @ModelAttribute JoinForm joinForm, BindingResult bindingResult){
    log.info("joinForm={}",joinForm);
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "member/SignUpUserGen";
    }

    log.info("joinForm.getPw()={}",joinForm.getPw());
    log.info("joinForm.getPwchk()={}",joinForm.getPwchk());
    //비밀번호 체크
    if(!joinForm.getPw().equals(joinForm.getPwchk())) {
      bindingResult.reject("pw","비밀번호가 동일하지 않습니다.");
      log.info("bindingResult={}",bindingResult);
      return "member/SignUpUserGen";
    }
    Members members = new Members();
    members.setMname(joinForm.getMname());
    members.setMid(joinForm.getMid());
    members.setMname(joinForm.getMname());
    members.setPw(joinForm.getPw());
    members.setEmail(joinForm.getEmail());
    members.setNickname(joinForm.getNickname());
    members.setPhone(joinForm.getPhone());
    members.setMaddress(joinForm.getMaddress());
    members.setMtype("n");
    members.setCompanyname(joinForm.getCompanyname());
    members.setBusinessnumber(joinForm.getBusinessnumber());

    log.info("members={}",members);
    membersSVC.memSave(members);
    return "member/SignUpSuccess";
  }

  @GetMapping("/signup/camp")
  public String campJoinForm(
      Model model,
      HttpSession session
  ){

    String sessionId = session.getId();
    log.info("sessionId={}",sessionId);
    JoinForm joinForm = new JoinForm();
    model.addAttribute("joinForm", joinForm);
    log.info("joinForm={}",joinForm);
    return "member/SignUpUserCamp";
  }

}
