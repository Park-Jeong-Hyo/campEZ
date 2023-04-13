package com.campEZ.Project0.uploadfile;

import com.campEZ.Project0.entity.UploadFile;
import com.campEZ.Project0.web.AttachFileType;

import java.util.List;
import java.util.Optional;

public interface UploadFileDAO {
  Long addFile(UploadFile uploadFile);

  void addFiles(List<UploadFile> uploadFiles);

  List<UploadFile> findFileByCode(AttachFileType attachFileType);

  List<UploadFile> findFilesByCodeWithRid(AttachFileType attachFileType, int rid);

  Optional<UploadFile> findFileByUploadFileId(int uploadfileId);

  int deleteFileByUploadFileId(int uploadfileId);

  int deleteFileByCodeWithRid(AttachFileType attachFileType, Long rid);
}
