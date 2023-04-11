package com.campEZ.Project0.web;

import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.members.svc.MembersSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageCommonController {
    private final CampingSVC campingSVC;
    private final MembersSVC membersSVC;


//    회원 정보 수정하기
    @GetMapping("/{mid}/common")
    public String myPageCommon(@PathVariable String mid,Model model){
//        Members member = membersSVC.memFindN(mid);
//        log.info("member={}",members);
//        String type = String.valueOf(member.getMtype());
//        System.out.println(type);
//
//        if ( type.equals("b")) {
//            System.out.println("타입이 맞음");
//            return "mypage/myPage__manager";
//        } else {
//            System.out.println("타입이 아님");
//            return "mypage/myPage__common";
//        }
        try {
            Members members = membersSVC.memFindN(mid);
            Members membersForm = new Members();

            membersForm.setBusinessnumber(members.getBusinessnumber());
            membersForm.setPw(members.getPw());
            membersForm.setMid(members.getMid());
            membersForm.setEmail(members.getEmail());
            membersForm.setMaddress(members.getMaddress());
            membersForm.setCompanyname(members.getCompanyname());
            membersForm.setMname(members.getMname());
            membersForm.setMtype(members.getMtype());
            membersForm.setNickname(members.getNickname());
            membersForm.setPhone(members.getPhone());

            model.addAttribute("members", membersForm);
        }catch (EmptyResultDataAccessException e){return null;}
          return "/mypage/myPage__common";
      }
//    회원 정보 수정 처리하기
    @PostMapping("/{mid}/common")
    public String commonEdit(
            @ModelAttribute Members membersForm, RedirectAttributes redirectAttributes,
            @PathVariable String mid) {
        Members members = new Members();

        members.setPw(membersForm.getPw());
        members.setMid(membersForm.getMid());
        members.setEmail(membersForm.getEmail());
        members.setMaddress(membersForm.getMaddress());
        members.setCompanyname(membersForm.getCompanyname());
        members.setMname(membersForm.getMname());
        members.setMtype(membersForm.getMtype());
        members.setNickname(membersForm.getNickname());
        members.setPhone(membersForm.getPhone());

       membersSVC.memUpdate(mid, members);
        redirectAttributes.addAttribute("mid",members.getMid());
        return "redirect:/mypage/{mid}/common";
        }
    }

//    회원 탈퇴, 예약 목록, 예약 취소, 타임리프로 값 받기, html에서 링크들 연결