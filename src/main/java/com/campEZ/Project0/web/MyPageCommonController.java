package com.campEZ.Project0.web;

import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.members.svc.MembersSVC;
import jakarta.servlet.http.HttpSession;
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

    //    일반회원 정보조회 및 수정
    @GetMapping("/{mid}/common")
    public String myPageCommon(
        @PathVariable String mid,
        Model model,
        HttpSession session
        ){
        LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Members members = membersSVC.memFindN(mid);
        log.info("members={}", members);
        String type = String.valueOf(members.getMtype());
        System.out.println(type);
        String memberId = String.valueOf(members.getMid());
        String loginId = String.valueOf(loginMembers.getMid());
        System.out.println(memberId);
        System.out.println(loginId);
        if (type.equals("n") && memberId.equals(loginId)) {
            try {
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
        } else {
            System.out.println("타입이 아님");
            return "errorPage/preparing";
        }
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

    // 일반회원 탈퇴
    @GetMapping("/{id}/gen/del")
    public String genDelete(
        @PathVariable("id") String mid,
        Model model,
        HttpSession session
    ) {
        Members members = membersSVC.memFindB(mid);
        LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);

        String membersId = String.valueOf(members.getMid());
        String loginId = String.valueOf(loginMembers.getMid());
        log.info("membersId={}",membersId);
        log.info("loginId={}",loginId);
        if ( membersId.equals(loginId) ) {
            membersSVC.memDelete(mid);
            return "mainPage/mainPage";
        } else {
            return "community/question";
        }
    }

    }

//    회원 탈퇴, 예약 목록, 예약 취소, 타임리프로 값 받기, html에서 링크들 연결