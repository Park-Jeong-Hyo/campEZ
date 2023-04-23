package com.campEZ.Project0.web.form.home;

import com.campEZ.Project0.entity.UploadFile;
import lombok.Data;

import java.util.List;

@Data
public class HomeUpload {

  private UploadFile imagedFile1;  //캠핑장 대표 이미지 불러오기
  private UploadFile imagedFile2;  //캠핑장 대표 이미지 불러오기
  private UploadFile imagedFile3;  //캠핑장 대표 이미지 불러오기
  private List<UploadFile> imagedFiles;  //캠핑장 대표 이미지 불러오기
}
