package com.campEZ.Project0.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders{
  private int onumber;
  private int cnumber;
  private int area;
  private String mid;
  private String phone;
  private int headcount;
  private String checkin;
  private String checkout;
}
