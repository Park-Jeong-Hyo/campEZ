package com.campEZ.Project0.svc;

import com.campEZ.Project0.dao.OrdersDAO;
import com.campEZ.Project0.entity.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
