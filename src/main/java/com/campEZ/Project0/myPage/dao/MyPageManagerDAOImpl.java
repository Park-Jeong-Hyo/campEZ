package com.campEZ.Project0.myPage.dao;

import com.campEZ.Project0.entity.Camping;
import com.campEZ.Project0.entity.Orders;

import java.lang.reflect.Member;
import java.util.List;

public class MyPageManagerDAOImpl implements  MyPageManagerDAO{
  @Override
  public List<Orders> findByEmail(String Email) {
    return null;
  }

  @Override
  public Member findMemberByEmail(String email) {
    return null;
  }

  @Override
  public void updateMember(Member member) {

  }

  @Override
  public void deleteMember(String mid) {

  }

  @Override
  public List<Camping> findMyCampByEmail(String companyname) {
    return null;
  }

  @Override
  public List<Orders> findByMID(String companyname) {
    return null;
  }
}
