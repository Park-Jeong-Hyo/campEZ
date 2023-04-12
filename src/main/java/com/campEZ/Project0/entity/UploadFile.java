package com.campEZ.Project0.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile{
  private Long upnumber;   // upnumber	NUMBER(4)
  private String code;     // CODE	VARCHAR2(5 BYTE)
  private Long rid;
  private String storename;
  private String uploadname;
  private String fsize;
  private String ftype;
  private String cdate;
  private String udate;
}
