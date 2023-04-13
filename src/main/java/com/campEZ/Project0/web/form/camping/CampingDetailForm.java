package com.campEZ.Project0.web.form.camping;

import com.campEZ.Project0.entity.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

  private MultipartFile imageFile1; //캠핑장 대표 이미지 다운로드
  private MultipartFile imageFile2; //안전시설 및 배치도 다운로드
  private List<MultipartFile> imageFiles1; //캠핑장 주요 시설 다운로드
  private List<MultipartFile> imageFiles2; //캠핑장 이미지 다운로드

  private UploadFile imagedFile1;  //캠핑장 대표 이미지 불러오기
  private UploadFile imagedFile2;  //안전시설 및 배치도 불러오기
  private List<UploadFile> imagedFiles1;  //캠핑장 주요 시설 불러오기
  private List<UploadFile> imagedFiles2;  //캠핑장 이미지 불러오기

}
