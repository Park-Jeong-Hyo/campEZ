package com.campEZ.Project0.web.form.camping;

import com.campEZ.Project0.entity.UploadFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampingSaveForm {
  private int cnumber;
  private String mid;
  //  @NotBlank
  private String cname;
  @NotBlank
  private String caddress;
  @NotBlank
  private String camptel;
  private String ctype;
  @NotBlank
  private String operdate;
  private String homepage;
  private String ctitle; //입력하는 창이 없다. 테이블 삭제 필요
  //  @NotBlank
  private String ctext;
  @NotNull
  private int priceweekend;
  @NotNull
  private int priceweekday;
  private String toilet;
  private String mart;
  private String udate;

  private int area;

  private Integer capacitys1;
  private Integer capacitys2;
  private Integer capacitys3;
  private Integer capacitys4;
  private Integer capacitys5;
  private Integer capacitys6;
  private Integer capacitys7;
  private Integer capacitys8;
  private Integer capacitys9;
  private Integer capacitys10;

  private MultipartFile imageFile; //캠핑장 대표 이미지 다운로드
  private List<MultipartFile> imageFiles; //캠핑장 이미지 다운로드

  private UploadFile imagedFile;  //캠핑장 대표 이미지 불러오기
  private List<UploadFile> imagedFiles;  //캠핑장 이미지 불러오기

}
