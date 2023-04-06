package com.campEZ.Project0.myPage.dao;

import com.campEZ.Project0.entity.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MyPageCommonDAOImpl implements MyPageCommonDAO{
  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<Orders> findByEmail(int mid) {
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
}

