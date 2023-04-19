package com.campEZ.Project0.web;

import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Camparea;
import com.campEZ.Project0.entity.Camping;
import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.entity.Orders;
import com.campEZ.Project0.members.svc.MembersSVC;
import com.campEZ.Project0.orders.svc.OrdersSVC;
import com.campEZ.Project0.web.form.camping.CampingSearchForm;
import com.campEZ.Project0.web.form.myPage.OrdersNameForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final OrdersSVC ordersSVC;

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
                CampingSearchForm areaForm = new CampingSearchForm();
                int maxNumber = 0;
                for(int i = 0; i < myCamp.size() ; i++) {
                    List<Camparea> compareList = campingSVC.campareaDetail(myCamp.get(i).getCnumber()).orElseThrow();
                    for(int j = 0; j < compareList.size() ; j++ ) {
                        if(maxNumber < compareList.get(j).getArea()) {
                            maxNumber = compareList.get(j).getArea();
                            areaForm.setArea(maxNumber);
                        };
                    };
                };
                model.addAttribute("areaForm", areaForm);
                model.addAttribute("myCamp", myCamp);
//            예약 현황 보기
                List<Orders> myOrders = membersSVC.orderFind(mid);
                log.info("myOrders={}",myOrders);
                model.addAttribute("myOrders", myOrders);
//            내 캠핑장 예약 관리
                List<OrdersNameForm> OrdersNameForm = membersSVC.orderFindB(mid);
                log.info("OrdersNameForm={}",OrdersNameForm);
                model.addAttribute("OrdersNameForm", OrdersNameForm);
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
    //캠핑장 삭제
    @GetMapping("/{mid}/manager/campingDelete")
    public String campingDelete(
            @ModelAttribute Camping item,
            BindingResult bindingResult,
            @PathVariable("mid") String mid,
            RedirectAttributes redirectAttributes
    ) {
        if(bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
        }
        int cnumber = item.getCnumber();
        log.info("cnumber={}", cnumber);
        campingSVC.campingDelete(cnumber);
        campingSVC.campareaDelete(cnumber);
        log.info("삭제됨");
        redirectAttributes.addAttribute("mid", mid);
        return "redirect:/mypage/{mid}/manager";
    }
//     예약 취소
    @GetMapping("/{onumber}/manager/del")
    public String OrderDelete(
        @PathVariable("onumber") Integer onumber,
        Model model){
        ordersSVC.orDelete(onumber);
        return "redirect:/";
    };
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