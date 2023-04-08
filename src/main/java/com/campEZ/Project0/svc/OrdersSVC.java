package com.campEZ.Project0.svc;

import com.campEZ.Project0.entity.Orders;

public interface OrdersSVC {

  //예약
  Orders order(Orders orders);

  //예약 취소
  int orDelete(int onumber);

}
