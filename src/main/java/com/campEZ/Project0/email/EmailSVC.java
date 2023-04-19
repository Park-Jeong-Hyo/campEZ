package com.campEZ.Project0.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSVC {
  @Autowired
  private JavaMailSender mailSender;

  public void sendEmail(String toEmail,
                        String subject,
                        String body){
    SimpleMailMessage message = new SimpleMailMessage();
//    보내는 사람 주소
    message.setFrom("내 네이버 메일 주소");
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
  }
}
