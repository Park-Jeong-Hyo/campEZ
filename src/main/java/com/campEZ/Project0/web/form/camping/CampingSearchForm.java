package com.campEZ.Project0.web.form.camping;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CampingSearchForm {

  private char ctype;       //캠핑타입
  private String caddress;  //캠핑지역
  @NotBlank
  private String cname;     //캠핑검색어
  private String camptel;   //캠핑장 전화번호
}
