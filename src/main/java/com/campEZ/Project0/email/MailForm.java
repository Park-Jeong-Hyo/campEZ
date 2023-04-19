package com.campEZ.Project0.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailForm {
//  메일 받는 사람 주소
  private String toEmail;
//  제목
  private String subject;
//  내용물
  private String body;
}
