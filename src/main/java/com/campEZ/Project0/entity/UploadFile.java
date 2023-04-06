package com.campEZ.Project0.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile{
  private int upnumber;
  private char position;
  private int reference;
  private String storename;
  private String uploadname;
  private String fsize;
  private String ftype;
  private String cdate;
  private String udate;
}
