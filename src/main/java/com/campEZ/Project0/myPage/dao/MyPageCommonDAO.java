package com.campEZ.Project0.myPage.dao;

import com.campEZ.Project0.entity.Orders;

import java.lang.reflect.Member;
import java.util.List;

public interface MyPageCommonDAO {
//  예약 현황
List<Orders> findByEmail(int mid);
  // 내 회원 정보
  Member findMemberByEmail(String email);
  //  회원 정보 수정
  void updateMember(Member member);
  // 회원 탈퇴
  void deleteMember(String mid);
}
