package com.campEZ.Project0.members.svc;

import com.campEZ.Project0.members.dao.MembersDAO;
import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.entity.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
  public Orders orderFindB(int cnumber){
    return membersDAO.orderFindB(cnumber);
  }

  //예약조회(일반회원)
  @Override
  public Orders orderFindN(String mid){
    return membersDAO.orderFindN(mid);
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

  //로그인
  @Override
  public Optional<Members> login(String mid, String pw){
    return membersDAO.login(mid,pw);
  }
}
