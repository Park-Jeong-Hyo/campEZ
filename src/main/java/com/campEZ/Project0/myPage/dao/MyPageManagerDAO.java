package com.campEZ.Project0.myPage.dao;

import com.campEZ.Project0.entity.Camping;
import com.campEZ.Project0.entity.Orders;

import java.lang.reflect.Member;
import java.util.List;

public interface MyPageManagerDAO {
  //  예약 현황
  List<Orders> findByEmail(String Email);
  // 내 회원 정보
  Member findMemberByEmail(String email);
  //  회원 정보 수정
  void updateMember(Member member);
  // 회원 탈퇴
  void deleteMember(String mid);
//  내 캠핑장
List<Camping> findMyCampByEmail(String companyname);
//  예약 관리
List<Orders> findByMID(String companyname);
}
