package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.members.svc.MembersSVC;
import com.campEZ.Project0.web.rest.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class ApiMemberController {

  private final MembersSVC membersSVC;

  // 아이디 중복확인
  @PostMapping("/idchk/{id}")
  public Response<Object> idChk(
      @PathVariable("id") String id
      ) {
    Response<Object> res = null;
    boolean idchk = membersSVC.isExist(id);

    if (idchk == false) {
      res =  Response.createRestResponse  ("00", "사용가능한 아이디", idchk);
    } else {
      res =  Response.createRestResponse("99", "중복되는 아이디 존재", idchk);
    }
    return res;
  }
  // 닉네임 중복확인
  @PostMapping("/nnchk/{nickname}")
  public Response<Object> nicknameChk(
      @PathVariable("nickname") String nickname
  ) {
    Response<Object> res = null;
    boolean nicknameChk = membersSVC.nnIsExist(nickname);
    if (nicknameChk == false) {
      res =  Response.createRestResponse  ("00", "사용 가능한 닉네임", nicknameChk);
    } else {
      res =  Response.createRestResponse("99", "이미 사용 중인 닉네임", nicknameChk);
    }
    return res;
  }

  // 이메일 중복확인
  @PostMapping("/emchk/{emil}")
  public Response<Object> emilChk(
      @PathVariable("emil") String emil
  ) {
    Response<Object> res = null;
    boolean emilChk = membersSVC.mailIsExist(emil);
    log.info("emilChk={}",emilChk);
    if (emilChk == false) {
      res =  Response.createRestResponse  ("00", "사용 가능한 이메일", emilChk);
    } else {
      res =  Response.createRestResponse("99", "이미 사용 중인 이메일", emilChk);
    }
    return res;
  }

  // 사업자 중복 체크
  @PostMapping("/bizchk/{biznum}")
  public Response<Object> bizChk(
      @PathVariable("biznum") String businessnumber
  ) {
    Response<Object> res = null;
    boolean bizchk = membersSVC.bizIsExist(businessnumber);
    if (bizchk == false) {
      res =  Response.createRestResponse  ("00", "등록 가능한 사업자", bizchk);
    } else {
      res =  Response.createRestResponse("99", "이미 등록된 사업자", bizchk);
    }
    return res;
  }

  // 아이디 찾기
  @GetMapping("/findid/{name}/{email}/{phone}")
  public Response<Object> findbyid(
      @PathVariable("name") String name,
      @PathVariable("email") String email,
      @PathVariable("phone") String phone
      ) {
    Response<Object> res = null;
    Optional<String> idFind = membersSVC.idFind(name, phone, email);
    String members = idFind.orElseThrow();
    if (members != null) {
      res =  Response.createRestResponse  ("00", "가입된 회원", members);
    } else {
      res =  Response.createRestResponse("99", "가입되지 않은 회원", members);
    }
    return res;
  }

  // 비밀번호 찾기
  @GetMapping("/findpw/{id}/{email}/{phone}")
  public Response<Object> findbypw(
      @PathVariable("id") String id,
      @PathVariable("email") String email,
      @PathVariable("phone") String phone
  ) {
    Response<Object> res = null;
    log.info("id={}",id);
    log.info("email={}",email);
    log.info("phone={}",phone);
    Members members = membersSVC.memFindB(id);
    boolean a = members.getEmail().equals(email);
    boolean b = members.getPhone().equals(phone);
    log.info("a={}",a);
    log.info("b={}",b);
    if ( a == true && b == true ) {
      res =  Response.createRestResponse  ("00", "가입된 회원", members);
      log.info("res={}",res);
    } else {
      res =  Response.createRestResponse("99", "가입되지 않은 회원", members);
      log.info("res={}",res);
    }
    return res;
  }
}
