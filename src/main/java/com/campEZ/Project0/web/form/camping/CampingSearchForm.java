package com.campEZ.Project0.web.form.camping;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampingSearchForm {
  private int cnumber;      //캠핑장번호
  private String ctype;       //캠핑타입
  private String caddress;  //캠핑지역
  @NotBlank
  private String cname;     //캠핑검색어
  private String camptel;   //캠핑장 전화번호
}
