package com.campEZ.Project0.common.uploadFile.svc;

import com.campEZ.Project0.entity.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.campEZ.Project0.common.uploadFile.dao.uploadFileDAO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class uploadfileSVCImpl implements uploadFileSVC{
  private final uploadFileDAO uploadFileDAO;

  @Override
  public int fileSaveSingle(UploadFile uploadFile) {
    return uploadFileDAO.fileSaveSingle(uploadFile);
  }

  @Override
  public void fileSaveMultiple(List<UploadFile> uploadFiles) {
  }
}
