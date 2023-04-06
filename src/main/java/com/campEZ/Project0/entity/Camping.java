package com.campEZ.Project0.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Camping {
  private int cnumber;
  private String mid;
  private String cname;
  private String caddress;
  private String camptel;
  private char ctype;
  private String operdate;
  private String homepage;
  private String ctitle;
  private String ctext;
  private int priceweekend;
  private int priceweekday;
  private char toilet;
  private char mart;
  private String udate;
}
