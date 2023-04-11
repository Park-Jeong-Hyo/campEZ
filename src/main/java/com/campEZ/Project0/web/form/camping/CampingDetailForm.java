package com.campEZ.Project0.web.form.camping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampingDetailForm {
  private int cnumber;
  private String mid;
  private String cname;
  private String caddress;
  private String camptel;
  private String ctype;
  private String operdate;
  private String homepage;
  private String ctitle;
  private String ctext;
  private int priceweekend;
  private int priceweekday;
  private String toilet;
  private String mart;
  private String udate;
}
