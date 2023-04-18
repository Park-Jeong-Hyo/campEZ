package com.campEZ.Project0.web;

import com.campEZ.Project0.members.svc.MembersSVC;
import com.campEZ.Project0.web.rest.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    log.info("nickname={}",nickname);
    boolean nicknameChk = membersSVC.nnIsExist(nickname);
    log.info("nickname={}",nickname);
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
}
