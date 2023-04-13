package com.campEZ.Project0.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile{
  private Long upnumber;    // upnumber	NUMBER(4)
  private String code;      // CODE	VARCHAR2(5 BYTE)
  private int rid;          // number(4) not null
  private String storename; // varchar2(50)
  private String uploadname;// varchar2(100)
  private String fsize;     // varchar2(45)
  private String ftype;     // varchar2(50)
  private String cdate;     // date DEFAULT TO_DATE(sysdate,'yyyy-mm-dd hh24:mi:ss')
  private String udate;     // date DEFAULT TO_DATE(sysdate,'yyyy-mm-dd hh24:mi:ss')
}

