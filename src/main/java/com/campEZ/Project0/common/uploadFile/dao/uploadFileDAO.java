package com.campEZ.Project0.common.uploadFile.dao;

import com.campEZ.Project0.entity.UploadFile;

import java.util.List;

public interface uploadFileDAO {
  // 업로드 파일 등록 (하나)
  int fileSaveSingle(UploadFile uploadfile);
  // 업로드 파일 등록 (다수)
  void fileSaveMultiple(List<UploadFile> uploadFiles);
}
