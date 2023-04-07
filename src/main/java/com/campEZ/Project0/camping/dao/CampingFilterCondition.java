package com.campEZ.Project0.camping.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CampingFilterCondition {
  //시설을 배열로 받아서ㅐ
  private String campingType;       //캠핑장 종류
  private String campingRegion;     //캠핑장 지역
  private String[] campingFacility; //캠핑장 시설
  private String campingKeyword;    //검색어
}
