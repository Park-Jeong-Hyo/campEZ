package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.entity.Orders;
import com.campEZ.Project0.members.svc.MembersSVC;
import com.campEZ.Project0.orders.svc.OrdersSVC;
import com.campEZ.Project0.web.form.myPage.MembersForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageCommonController {
    private final MembersSVC membersSVC;
    private final OrdersSVC ordersSVC;

    //    일반회원 정보조회 및 수정
    @GetMapping("/{mid}/common")
    public String myPageCommon(
        @PathVariable String mid,
        Model model,
        HttpSession session
        ){
        LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);
        //일반 회원인지 확인하는 로직
        try {
            if(loginMembers.getMid().equalsIgnoreCase("n"))
                log.info("일반 회원입니다.");
        } catch (NullPointerException e) {
            log.info("일반회원이 아닙니다.");
            return "redirect:/";
        }

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
                MembersForm membersForm = new MembersForm();

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
            //            예약 현황 보기
                List<Orders> myOrders = membersSVC.orderFind(mid);
                model.addAttribute("myOrders", myOrders);
            } catch (EmptyResultDataAccessException e){
                return "/mypage/myPage__common";
            }
            return "/mypage/myPage__common";
        } else {
            System.out.println("타입이 아님");
            return "errorPage/preparing";
        }
    }
//    회원 정보 수정 처리하기
    @PostMapping("/{mid}/common")
    public String commonEdit(
            @ModelAttribute MembersForm membersForm,
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
        return "myPage/myPageInfoSuccess";
        }
    //     예약 취소
    @GetMapping("/{onumber}/common/del")
    public String OrderDelete(
        @PathVariable("onumber") Integer onumber,
        Model model){
        ordersSVC.orDelete(onumber);
        return "myPage/myPageCancelSuccess";
    };
    // 일반회원 탈퇴
    @GetMapping("/{id}/gen/del")
    public String genDelete(
        @PathVariable("id") String mid,
        Model model,
        HttpSession session
    ) {
        LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Members members = membersSVC.memFindN(mid);
        log.info("members={}", members);
        String type = String.valueOf(members.getMtype());
        System.out.println(type);
        String memberId = String.valueOf(members.getMid());
        String loginId = String.valueOf(loginMembers.getMid());
        System.out.println(memberId);
        System.out.println(loginId);
        if ( type.equals("n") && memberId.equals(loginId) ) {
            System.out.println("발동됨");
            try {
                membersSVC.memDelete(mid);
            }catch (org.springframework.dao.DataIntegrityViolationException e){
                return "redirect:/remain";
            }catch (Exception e){
                return "redirect:/unknown";
            }
            session.invalidate();
            return "myPage/myPageResignSuccess";
        } else {
            return "redirect:/preparing";
        }
    }

    }
