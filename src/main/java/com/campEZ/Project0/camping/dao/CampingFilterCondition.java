package com.campEZ.Project0.camping.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CampingFilterCondition {
  private String campingType;       //캠핑장 종류
  private String campingRegion;     //캠핑장 지역
//  private String[] campingFacility; //캠핑장 시설  현재 개발 상황에서는 시설을 추가할 시간이 없음.
  private String campingKeyword;    //검색어
}
