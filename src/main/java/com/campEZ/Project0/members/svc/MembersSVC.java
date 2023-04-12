package com.campEZ.Project0.members.svc;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.entity.Orders;
import com.campEZ.Project0.entity.OrdersAndCName;

import java.util.List;
import java.util.Optional;

public interface MembersSVC {
  //회원등록
  Members memSave(Members members);

  //회원수정
  Members memUpdate(String mid, Members members);

  //회원탈퇴
  void memDelete(String mid);

  //회원조회(사업자)
  Members memFindB(String mid);

  //회원조회(일반회원)
  Members memFindN(String mid);

  //예약조회(사업자)
  List<OrdersAndCName> orderFindB(String mid);

  //예약조회(일반회원)
  List<Orders> orderFind(String mid);

  //아이디찾기
  Optional<String> idFind(String mname, String phone, String email);

  //비밀번호찾기
  Optional<String> pwFind(String mid, String phone,String email);

  //회원유무
  boolean isExist(String mid);

  //로그인
  Optional<Members> login(String mid, String pw);
}
