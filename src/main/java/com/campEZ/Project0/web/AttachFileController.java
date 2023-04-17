package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.UploadFile;
import com.campEZ.Project0.uploadfile.UploadFileSVC;
import com.campEZ.Project0.web.exception.BizException;
import com.campEZ.Project0.web.rest.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachFileController {

  private final UploadFileSVC uploadFileSVC;

  //첨부파일 다운로드
  @GetMapping("/down/{fid}")
  public ResponseEntity<Resource> downLoadAttach(
      @PathVariable("fid") int upnumber
  ) throws MalformedURLException {

    Optional<UploadFile> uploadFile = uploadFileSVC.findFileByUploadFileId(upnumber);
    if(uploadFile.isEmpty()) new BizException("첨부파일을 찾을 수 없습니다.");

    //분류코드->enum변환
    AttachFileType attachFileType = AttachFileType.valueOf(uploadFile.get().getCode());
    //첨부파일
    String storeFilename = uploadFileSVC.getStoreFilename(attachFileType, uploadFile.get().getStorename());
    Resource resource = new UrlResource("file:"+storeFilename);

    //한글파일명 깨짐 방지를위한 인코딩
    String encodeUploadFileName = UriUtils.encode(uploadFile.get().getUploadname(), StandardCharsets.UTF_8);
    //Http응답 메세지 헤더에 첨부파일이 있음을 알림
    String contentDisposition = "attachment; filename="+ encodeUploadFileName;

    return ResponseEntity.ok()  //응답코드 200
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .body(resource);
  }

  //이미지 뷰
  @GetMapping("/view/{fid}")
  public ResponseEntity<Resource> viewAttach(
      @PathVariable("fid") int upnumber
  ) throws MalformedURLException {

    Optional<UploadFile> uploadFile = uploadFileSVC.findFileByUploadFileId(upnumber);
    if(uploadFile.isEmpty()) new BizException("첨부파일을 찾을 수 없습니다.");
    //분류코드->enum변환
    AttachFileType attachFileType = AttachFileType.valueOf(uploadFile.get().getCode());
    //첨부파일
    String storeFilename = uploadFileSVC.getStoreFilename(attachFileType, uploadFile.get().getStorename());
    Resource resource = new UrlResource("file:"+storeFilename);
//    //한글파일명 깨짐 방지를위한 인코딩
//    String encodeUploadFileName = UriUtils.encode(uploadFile.get().getUploadname(), StandardCharsets.UTF_8);
//    //Http응답 메세지 헤더에 첨부파일이 있음을 알림
//    String contentDisposition = "attachment; filename="+ encodeUploadFileName;

    return ResponseEntity.ok()  //응답코드 200
        .body(resource);
  }

  //첨부파일 삭제-단건
  @ResponseBody
  @DeleteMapping("/{fid}")
  public RestResponse<Object> deleteFileByFid(
      @PathVariable int fid) {
    int affectedRow = uploadFileSVC.deleteFileByUploadFileId(fid);

    RestResponse<Object> result = null;
    if(affectedRow == 1){
      result = RestResponse.createRestResponse("00", "성공", null);
    }else{
      result = RestResponse.createRestResponse("99", "fail", null);
    }
    return result;
  }
}