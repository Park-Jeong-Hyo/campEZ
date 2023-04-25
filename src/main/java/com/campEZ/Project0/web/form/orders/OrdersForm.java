package com.campEZ.Project0.web.form.orders;


import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrdersForm {

  private int cnumber;
  private int area;
  private String mid;
  @Size(min=13,max=13, message = "올바른 연락처를 입력해 주십시오")
  private String phone;
  private int headcount;
  private String checkin;
  private String checkout;

}
