package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.members.svc.MembersSVC;
import com.campEZ.Project0.web.rest.RestResponse;
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
  public RestResponse<Object> idChk(
      @PathVariable("id") String id
      ) {
    RestResponse<Object> res = null;
    boolean idchk = membersSVC.isExist(id);

    if (idchk == false) {
      res =  RestResponse.createRestResponse  ("00", "사용가능한 아이디", idchk);
    } else {
      res =  RestResponse.createRestResponse("99", "중복되는 아이디 존재", idchk);
    }
    return res;
  }
  // 닉네임 중복확인
  @PostMapping("/nnchk/{nickname}")
  public RestResponse<Object> nicknameChk(
      @PathVariable("nickname") String nickname
  ) {
    RestResponse<Object> res = null;
    boolean nicknameChk = membersSVC.nnIsExist(nickname);
    if (nicknameChk == false) {
      res =  RestResponse.createRestResponse  ("00", "사용 가능한 닉네임", nicknameChk);
    } else {
      res =  RestResponse.createRestResponse("99", "이미 사용 중인 닉네임", nicknameChk);
    }
    return res;
  }

  // 사업자 중복 체크
  @PostMapping("/bizchk/{biznum}")
  public RestResponse<Object> bizChk(
      @PathVariable("biznum") String businessnumber
  ) {
    RestResponse<Object> res = null;
    boolean bizchk = membersSVC.bizIsExist(businessnumber);
    if (bizchk == false) {
      res =  RestResponse.createRestResponse  ("00", "등록 가능한 사업자", bizchk);
    } else {
      res =  RestResponse.createRestResponse("99", "이미 등록된 사업자", bizchk);
    }
    return res;
  }

  // 아이디 찾기
  @GetMapping("/findid/{name}/{email}/{phone}")
  public RestResponse<Object> findbyid(
      @PathVariable("name") String name,
      @PathVariable("email") String email,
      @PathVariable("phone") String phone
      ) {
    RestResponse<Object> res = null;
    Optional<String> idFind = membersSVC.idFind(name, phone, email);
    String members = idFind.orElseThrow();
    if (members != null) {
      res =  RestResponse.createRestResponse  ("00", "가입된 회원", members);
    } else {
      res =  RestResponse.createRestResponse("99", "가입되지 않은 회원", members);
    }
    return res;
  }

  // 비밀번호 찾기
  @GetMapping("/findpw/{id}/{email}/{phone}")
  public RestResponse<Object> findbypw(
      @PathVariable("id") String id,
      @PathVariable("email") String email,
      @PathVariable("phone") String phone
  ) {
    RestResponse<Object> res = null;
    log.info("id={}",id);
    log.info("email={}",email);
    log.info("phone={}",phone);
    Members members = membersSVC.memFindB(id);
    boolean a = members.getEmail().equals(email);
    boolean b = members.getPhone().equals(phone);
    log.info("a={}",a);
    log.info("b={}",b);
    if ( a == true && b == true ) {
      res =  RestResponse.createRestResponse  ("00", "가입된 회원", members);
      log.info("res={}",res);
    } else {
      res =  RestResponse.createRestResponse("99", "가입되지 않은 회원", members);
      log.info("res={}",res);
    }
    return res;
  }
}
