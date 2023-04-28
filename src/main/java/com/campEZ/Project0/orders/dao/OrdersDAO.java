package com.campEZ.Project0.orders.dao;

import com.campEZ.Project0.entity.Orders;

import java.util.List;

public interface OrdersDAO {

  //예약
  Orders order(Orders orders);

  //예약 취소
  int orDelete(int onumber);

  //캠프에리어 캠핑장번호로 구역 가져오기
  List<Integer> campingFindByCnumber(int cnumber);

  //캠프에리어 테이블에서 캠핑장번호, 구역으로 인원 가져오기
  Integer campingAreaChange(int cnumber, int area);

  //캠핑테이블에서 캠핑장번호로 소개글 가져오기
  String campingCtext(int cnumber);

  //캠핑테이블에서 캠핑장번호로 이름 가져오기
  String campingCname(int cnumber);
  // 예약 번호로 예약 확정하기
  int campingChk(int onumber);

  // 예약 존재 확인
  boolean orderIsExist(int cnumber);

}
