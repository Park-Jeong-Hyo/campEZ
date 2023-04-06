package com.campEZ.Project0.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Members {
  private String mname;
  private String mid;
  private String pw;
  private String phone;
  private String email;
  private String nickname;
  private String maddress;
  private String mtype;
  private String companyname;
  private String businessnumber;
}
