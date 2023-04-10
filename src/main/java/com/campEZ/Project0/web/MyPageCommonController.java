package com.campEZ.Project0.web;

import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.members.svc.MembersSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    //    @GetMapping("/{mid}/common")
// 캠핑 목록 불러오기
//    public String Commonlist(@PathVariable String mid ,Model model,검색조건 검색조건){
//        List<Camping> list = campingSVC.전체찾는메소드(검색조건);
//        List<Camping> allList = new ArrayList<>();
//        for (Camping camping : list) {
//            Camping listForm = new Camping();
//            BeanUtils.copyProperties(camping, listForm);
//            allList.add(listForm);
//        }
//        model.addAttribute("list", allList);
//          }
//    회원 정보 수정하기
    @GetMapping("/{mid}/common")
    public String managerMemberMod(@PathVariable String mid,Model model){
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

            model.addAttribute("members",membersForm);
          return "/mypage/myPage__common";
      }
//    회원 정보 수정 처리하기
    @PostMapping("/commonUpdate")
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