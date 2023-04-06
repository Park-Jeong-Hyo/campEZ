package com.campEZ.Project0.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments{
  private int conumber;
  private int pnumber;
  private String nickname;
  private String cotext;
  private String udate;
}
