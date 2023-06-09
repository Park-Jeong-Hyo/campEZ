package com.campEZ.Project0.members.svc;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.entity.Orders;
import com.campEZ.Project0.members.dao.MembersDAO;
import com.campEZ.Project0.web.form.myPage.OrdersNameForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembersSVCImpl implements MembersSVC {

  private final MembersDAO membersDAO;

  //회원등록
  @Override
  public Members memSave(Members members){return membersDAO.memSave(members);}

  //회원수정
  @Override
  public Members memUpdate(String mid, Members members){
    membersDAO.memUpdate(mid,members);
    return members;
  }

  //회원탈퇴
  @Override
  public void memDelete(String mid){
    membersDAO.memDelete(mid);
  }

  //회원조회(사업자)
  @Override
  public Members memFindB(String mid){
    return membersDAO.memFindB(mid);
  }

  //회원조회(일반회원)
  @Override
  public Members memFindN(String mid){
    return membersDAO.memFindN(mid);
  }

  //예약조회(사업자)
  @Override
  public List<OrdersNameForm> orderFindB(String mid){
    return membersDAO.orderFindB(mid);
  }

  //예약조회(일반회원)
  @Override
  public List<Orders> orderFind(String mid){
    return membersDAO.orderFind(mid);
  }

  //아이디찾기
  @Override
  public Optional<String> idFind(String mname, String phone, String email){
    return membersDAO.idFind(mname,phone,email);
  }

  //비밀번호찾기
  @Override
  public Optional<String> pwFind(String mid, String phone,String email){
    return membersDAO.pwFind(mid,phone,email);
  }

  //회원유무
  @Override
  public boolean isExist(String mid){return membersDAO.isExist(mid);}
  
  // 닉네임 중복체크
  @Override
  public boolean nnIsExist(String nickname) {
    return membersDAO.nnIsExist(nickname);
  }

  @Override
  public boolean mailIsExist(String email) {
    return membersDAO.mailIsExist(email);
  }

  // 사업자 중복확인
  @Override
  public boolean bizIsExist(String businessnumber) {
    return membersDAO.bizIsExist(businessnumber);
  }

  //로그인
  @Override
  public Optional<Members> login(String mid, String pw){
    return membersDAO.login(mid,pw);
  }

  // 비밀번호 변경
  @Override
  public void changePasswd(String mid, String pw) {
    membersDAO.changePasswd(mid,pw);
  }
}