package com.campEZ.Project0.uploadfile.dao;

import com.campEZ.Project0.entity.UploadFile;
import com.campEZ.Project0.web.AttachFileType;

import java.util.List;
import java.util.Optional;

public interface UploadFileDAO {
  Long addFile(UploadFile uploadFile);

  void addFiles(List<UploadFile> uploadFiles);

  List<UploadFile> findFilesByCodeWithRid(AttachFileType attachFileType, Long rid);

  Optional<UploadFile> findFileByUploadFileId(Long uploadfileId);

  int deleteFileByUploadFildId(Long uploadfileId);

  int deleteFileByCodeWithRid(AttachFileType attachFileType, Long rid);
}
