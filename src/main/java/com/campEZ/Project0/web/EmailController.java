package com.campEZ.Project0.web;

import com.campEZ.Project0.email.EmailSVC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Random;

@Controller
@RequiredArgsConstructor
public class EmailController {
  private final EmailSVC emailSVC;

  @GetMapping("/email")
  public String email(Model model){
    model.addAttribute("MailForm",new com.campEZ.Project0.email.MailForm());
    return "email/mail";
  }

  @PostMapping("/email")
  public String sendEmail(@ModelAttribute("MailForm") com.campEZ.Project0.email.MailForm mailForm, Model model){
//    암호 만들기
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    int length = 6;
    StringBuilder codeBuilder = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      int index = random.nextInt(characters.length());
      codeBuilder.append(characters.charAt(index));
    }
//    메일 내용
    mailForm.setBody(codeBuilder.toString());
    String code = "인증 번호는 "+mailForm.getBody()+" 입니다";
//    메일폼 : 메일 이름
    mailForm.setSubject("캠핑EZ 인증번호");
    emailSVC.sendEmail(mailForm.getToEmail(), mailForm.getSubject(),code );
    model.addAttribute("MailForm", mailForm);
    return "email/mail";
  }
}
