package com.campEZ.Project0.web.form.post;

import lombok.Data;

@Data
public class PostSaveForm {

  // 회원 닉네임
  private String nickname; //NICKNAME	VARCHAR2(20 BYTE)
  // 글 제목
  private String ptitle; //PTITLE	VARCHAR2(50 BYTE)
  // 글 내용
  private String ptext; //PTEXT	CLOB
  // 글 종류(공지,자유, 질문)
  private char ptype; // PTYPE	CHAR(1 BYTE)
  // 글 게시날짜
  private String udate; // UDATE	DATE

}
