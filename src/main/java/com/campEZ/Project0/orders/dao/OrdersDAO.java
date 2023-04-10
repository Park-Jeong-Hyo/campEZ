package com.campEZ.Project0.orders.dao;

import com.campEZ.Project0.entity.Orders;

public interface OrdersDAO {

  //예약
  Orders order(Orders orders);

  //예약 취소
  int orDelete(int onumber);
}
