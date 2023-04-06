package com.campEZ.Project0.common.uploadFile.svc;

import com.campEZ.Project0.entity.UploadFile;

import java.util.List;

public interface uploadFileSVC {
  int fileSaveSingle(UploadFile uploadFile);
  void fileSaveMultiple(List<UploadFile> uploadFiles);
}
