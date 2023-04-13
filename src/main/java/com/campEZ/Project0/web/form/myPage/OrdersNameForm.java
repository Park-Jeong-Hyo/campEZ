package com.campEZ.Project0.web.form.myPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersNameForm {
  private String cname;
  private int onumber;
  private int cnumber;
  private int area;
  private String mid;
  private String phone;
  private int headcount;
  private String checkin;
  private String checkout;
}
