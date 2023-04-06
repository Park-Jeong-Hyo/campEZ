package com.campEZ.Project0.common.uploadFile;

import com.campEZ.Project0.entity.UploadFile;
import com.campEZ.Project0.common.uploadFile.dao.uploadFileDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class uploadFileDAOImplTest {

  @Autowired
  uploadFileDAO uploadFileDAO;

  @Test
  @DisplayName("업로드 파일 등록(하나)")
  void fileSaveSingle() {
    UploadFile uploadFile = new UploadFile();
    uploadFile.setPosition('n');
    uploadFile.setReference(2);
    uploadFile.setStorename(UUID.randomUUID().toString());
    uploadFile.setUploadname("등록테스트");
    uploadFile.setFsize("9134");
    uploadFile.setFtype("png");
    System.out.println(uploadFileDAO.fileSaveSingle(uploadFile));
  }
}
