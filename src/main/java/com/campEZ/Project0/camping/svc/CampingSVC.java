package com.campEZ.Project0.camping.svc;

import com.campEZ.Project0.camping.dao.CampingFilterCondition;
import com.campEZ.Project0.entity.Camparea;
import com.campEZ.Project0.entity.Camping;
import com.campEZ.Project0.entity.UploadFile;

import java.util.List;
import java.util.Optional;

public interface CampingSVC {
  //캠핑장 등록
  Camping campingSave(Camping camping);
  //캠핑장 등록(이미지 포함)
  Camping campingSave(Camping camping, List<UploadFile> uploadFiles);
  //캠핑장 구역 등록
  Camparea campareaSave(Camparea camparea);
  //캠핑장 수정
  int campingUpdate(Camping camping, int cnumber);
  //캠핑장 구역 수정
  int campareaUpdate(Camparea camparea);
  //캠핑장 삭제
  int campingDelete(int cnumber);
  //캠핑장구역 삭제
  int campareaDelete(int cnumber);
  //캠핑장 조회
  Optional<Camping> campingDetail(int cnumber);
  //캠핑장 구역 조회
  Optional<List<Camparea>> campareaDetail(int cnumber);
  //  내 캠핑장 조회
  List<Camping> campingFindByManagerMid(String mid);
  //캠핑장 검색
  List<Camping> campingSearch(CampingFilterCondition campingFilterCondition);
}
