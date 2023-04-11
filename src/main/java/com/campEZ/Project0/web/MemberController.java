package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.members.svc.MembersSVC;
import com.campEZ.Project0.web.form.members.JoinForm;
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

  //회원가입양식
  @GetMapping("/add")
  public String joinForm(Model model){
    model.addAttribute("joinForm", new JoinForm());
    return "member/SignUpUserCamp";
  }

  //회원가입처리
  @PostMapping("/add")
  public String join(@Valid @ModelAttribute JoinForm joinForm, BindingResult bindingResult){
    log.info("joinForm={}",joinForm);
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "member/SignUpUserCamp";
    }

    //비밀번호 체크
    if(!joinForm.getPw().equals(joinForm.getPwchk())) {
      bindingResult.reject("pw","비밀번호가 동일하지 않습니다.");
      log.info("bindingResult={}",bindingResult);
      return "member/SignUpUserCamp";
    }
    Members members = new Members();
    members.setMname(joinForm.getMname());
    members.setMid(joinForm.getMid());
    members.setMname(joinForm.getMname());
    members.setPw(joinForm.getPw());
    members.setEmail(joinForm.getEmail());
    members.setNickname(joinForm.getNickname());
    members.setMaddress(joinForm.getMaddress());
    members.setMtype(joinForm.getMtype());
    members.setCompanyname(joinForm.getCompanyname());
    members.setBusinessnumber(joinForm.getBusinessnumber());

    membersSVC.memSave(members);
    return "member/SignUpSuccess";
  }
}
