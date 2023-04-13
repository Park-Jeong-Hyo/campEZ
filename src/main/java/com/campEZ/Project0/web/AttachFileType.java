package com.campEZ.Project0.web;

public enum AttachFileType {

  A01("캠핑장 대표 이미지"),
  A02("캠핑장 주요 시설"),
  A03("캠핑장 이미지"),
  A04("안전시설 및 배치도");


  private String description;

  AttachFileType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}