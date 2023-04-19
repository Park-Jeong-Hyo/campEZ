package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.members.svc.MembersSVC;
import com.campEZ.Project0.pwGenerator.PasswordGenerator;
import com.campEZ.Project0.web.form.members.FindIdForm;
import com.campEZ.Project0.web.form.members.FindPwForm;
import com.campEZ.Project0.web.form.members.JoinForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MembersSVC membersSVC;

  //회원가입양식(일반)
  @GetMapping("/signup/gen")
  public String genJoinForm(
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
  public String genJoin(
      @Valid @ModelAttribute JoinForm joinForm,
      BindingResult bindingResult
  ){

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

  //회원가입양식(사업자)
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

  //회원가입처리(사업자)
  @PostMapping("/signup/camp")
  public String campJoin(
      @Valid @ModelAttribute JoinForm joinForm,
      BindingResult bindingResult
  ){

    log.info("joinForm={}",joinForm);
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "member/SignUpUserCamp";
    }

    log.info("joinForm.getPw()={}",joinForm.getPw());
    log.info("joinForm.getPwchk()={}",joinForm.getPwchk());

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
    members.setPhone(joinForm.getPhone());
    members.setMaddress(joinForm.getMaddress());
    members.setMtype("b");
    members.setCompanyname(joinForm.getCompanyname());
    members.setBusinessnumber(joinForm.getBusinessnumber());

    log.info("members={}",members);
    membersSVC.memSave(members);
    return "member/SignUpSuccess";
  }

  // 아이디 비밀번호 찾기
  @GetMapping("/findbyidpw")
  public String findByIdPw(
      Model model,
      FindIdForm findIdForm,
      FindPwForm findPwForm
  ) {
    model.addAttribute("findIdForm",findIdForm);
    model.addAttribute("findPwForm",findPwForm);
    return "member/findByIdPw";
  }

  // 아이디 찾기 완료
  @GetMapping("findedid/{id}")
  public String findedId(
      @PathVariable("id") String id,
      Model model
  ) {
    log.info("id={}",id);
    Members member = new Members();
    member.setMid(id);
    log.info("model={}",model);
    model.addAttribute("id",id);
    model.getAttribute(id);
    log.info("model={}",model);
    return "member/FindIdSuccess";
  }

  // 비밀번호 변경
  @GetMapping("/changepw/{id}")
  public String findedpw(
      @PathVariable("id") String id
  ) {

    Members members = membersSVC.memFindB(id);
    PasswordGenerator.PasswordGeneratorBuilder passwordGeneratorBuilder = new PasswordGenerator.PasswordGeneratorBuilder();
    String pwd = passwordGeneratorBuilder
        .useDigits(true)  //숫자포함여부
        .useLower(true)   //소문자포함
        .useUpper(true)   //대문자포함
        .usePunctuation(true) //특수문자포함
        .build()
        .generate(10); //비밀번호 자리수

    membersSVC.changePasswd(members.getMid(), pwd);
    return "member/FindpwSuccess";
  }
}
