package com.campEZ.Project0.web;

public enum AttachFileType {

  A01("이미지파일");

  private String description;

  AttachFileType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}