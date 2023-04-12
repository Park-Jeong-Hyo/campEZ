package com.campEZ.Project0.web;

import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Camping;
import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.entity.Orders;
import com.campEZ.Project0.members.svc.MembersSVC;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageManagerController {
    private final CampingSVC campingSVC;
    private final MembersSVC membersSVC;

    //   캠핑장회원 정보조회 및 수정
    @GetMapping("/{mid}/manager")
    public String myPageManager(
        @PathVariable String mid,
        Model model,
        HttpSession session
        ) {
        LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Members members = membersSVC.memFindN(mid);
        String type = String.valueOf(members.getMtype());
        String memberId = String.valueOf(members.getMid());
        String loginId = String.valueOf(loginMembers.getMid());
        if (type.equals("b") && memberId.equals(loginId)) {
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
//            내 캠핑장 보기
            List<Camping> myCamp = campingSVC.campingFindByManagerMid(mid);
            log.info("myCamp={}",myCamp);
            model.addAttribute("myCamp", myCamp);
//            예약 현황 보기
            List<Orders> myOrders = membersSVC.orderFind(mid);
                log.info("myOrders={}",myOrders);
            model.addAttribute("myOrders", myOrders);
            }catch (EmptyResultDataAccessException e){return "/mypage/myPage__manager";}
            return "/mypage/myPage__manager";
        } else {
            System.out.println("타입이 아님");
            return "errorPage/preparing";
        }
    }

    //    회원 수정 처리
    @PostMapping("/{mid}/manager")
    public String managerEdit(
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
        redirectAttributes.addAttribute("mid", members.getMid());
        return "redirect:/mypage/{mid}/manager";
    }

    // 사업자 회원 탈퇴
    @GetMapping("/{id}/camp/del")
    public String CampDelete(
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
        if ( type.equals("b") && memberId.equals(loginId) ) {
            try {
                membersSVC.memDelete(mid);
            }catch (org.springframework.dao.DataIntegrityViolationException e){
                return "redirect:/remain";
            }catch (Exception e){
                return "redirect:/unknown";
            }
            session.invalidate();
            return "redirect:/";
        } else {
            return "redirect:/preparing";
        }
    }
}