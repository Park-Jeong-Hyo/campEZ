package com.campEZ.Project0.orders.svc;

import com.campEZ.Project0.orders.dao.OrdersDAO;
import com.campEZ.Project0.entity.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersSVCImpl implements OrdersSVC{

  private final OrdersDAO ordersDAO;

  //예약
  @Override
  public Orders order(Orders orders) {
    return ordersDAO.order(orders);
  }

  //예약 취소
  @Override
  public int orDelete(int onumber) {
    return ordersDAO.orDelete(onumber);
  }

  //캠프에리어 테이블에서 캠핑장번호로 구역 가져오기
  @Override
  public List<Integer> campingFindByCnumber(int cnumber){
    return ordersDAO.campingFindByCnumber(cnumber);
  }

  //캠프에리어 테이블에서 캠핑장번호, 구역으로 인원 가져오기
  @Override
  public Integer campingAreaChange(int cnumber, int area){
    return ordersDAO.campingAreaChange(cnumber, area);
  }

  //캠핑테이블에서 캠핑장번호로 소개글 가져오기
  @Override
  public String campingCtext(int cnumber){
    return ordersDAO.campingCtext(cnumber);
  }

  //캠핑테이블에서 캠핑장번호로 이름 가져오기
  @Override
  public String campingCname(int cnumber){
    return ordersDAO.campingCname(cnumber);
  }
  // 예약 번호로 예약 확정하기
  @Override
  public int campingChk(int onumber){return ordersDAO.campingChk(onumber);}
}
