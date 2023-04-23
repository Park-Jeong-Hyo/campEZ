package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.UploadFile;
import com.campEZ.Project0.members.svc.MembersSVC;
import com.campEZ.Project0.uploadfile.UploadFileSVC;
import com.campEZ.Project0.web.form.home.HomeUpload;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

  private final MembersSVC membersSVC;

  private final UploadFileSVC uploadFileSVC;

  // 메인페이지 맵핑
  @GetMapping
  public String Home(Model model) {
    HomeUpload homeUpload = new HomeUpload();
    List<UploadFile> imagedFiles = uploadFileSVC.findFileByCode(AttachFileType.A01);
    homeUpload.setImagedFiles(imagedFiles);
    if(imagedFiles.size() >= 1) {
      homeUpload.setImagedFile1(imagedFiles.get(0));
    }
    if(imagedFiles.size() >= 2) {
      homeUpload.setImagedFile2(imagedFiles.get(1));
    }
    if(imagedFiles.size() >= 3) {
      homeUpload.setImagedFile3(imagedFiles.get(2));
    }

    model.addAttribute("homeUpload",homeUpload);
    return "mainPage/mainPage";
  }

  // 회원가입페이지 맵핑
  @GetMapping("/signup")
  public String signUp() {
    return "member/SignUp";
  }

  // 마이페이지 맵핑
  @GetMapping("/mypage")
  public String mypage(
      HttpSession session,
      RedirectAttributes redirectAttributes
  ) {
    LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);
    String type = String.valueOf(loginMembers.getMtype());
    log.info("type={}",type);
    redirectAttributes.addAttribute("mid",loginMembers.getMid());
    log.info("redirectAttributes={}",redirectAttributes);
    if ( loginMembers.getMid() != null ) {
      if ( type.equals("b")) {
        System.out.println("b 타입");
        return "redirect:/mypage/{mid}/manager";
      } else {
        System.out.println("n 타입");
        return "redirect:/mypage/{mid}/common";
      }
    } else {
      return "mainPage/mainPage";
    }
  }

  // 준비 중 페이지 맵핑
  @GetMapping("/preparing")
  public String error() {
    return "errorPage/preparing";
  }
  // 데이터 남아있음 페이지 맵핑
  @GetMapping("/remain")
  public String remain() {
    return "errorPage/remain";
  }
  // 알 수 없는 에러 맵핑
  @GetMapping("/unknown")
  public String unknown() {
    return "errorPage/unknown";
  }

  // 사이트맵 맵핑
  @GetMapping("/sitemap")
  public String siteMap() {
    return "siteMap/SiteMap";
  }
}

// 로그인페이지 맵핑
//  @GetMapping("/login")
//  public String login() {
//    return "member/logIn";
//  }

//  // 검색페이지 맵핑
//  @GetMapping("/search")
//  public String search() {
//    return "search/searchListMain";
//  }

