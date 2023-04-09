package com.campEZ.Project0.web;

import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Camping;
import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.svc.MembersSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageManagerController {
    private final CampingSVC campingSVC;
    private final MembersSVC membersSVC;

//    @GetMapping("/{mid}/manager")
//        캠핑 목록 불러오기
//    public String Managerlist(@PathVariable String mid ,Model model,검색조건 검색조건){
//        List<Camping> list = campingSVC.전체찾는메소드(검색조건);
//        List<Camping> allList = new ArrayList<>();
//        for (Camping camping : list) {
//            Camping listForm = new Camping();
//            BeanUtils.copyProperties(camping, listForm);
//            allList.add(listForm);
//        }
//    model.addAttribute("list", allList);
//    회원 정보 수정하기
//    public String commonMemberMod(@PathVariable String mid,Model model){
//        Members members = membersSVC.memFindB(mid);
//        Members membersForm = new Members();
//
//        membersForm.set ;members의 모든 get값을 멤버스폼 셋값()안에 넣기
//
//        model.addAttribute("members",membersForm);
//    return "myPage/myPage_common";
//    회원 수정 처리
//    @PostMapping("/{mid}/managerUpdate")
//    public String managerEdit(
//            @ModelAttribute Members membersForm, RedirectAttributes redirectAttributes,
//            @PathVariable String mid) {
//        Members members = new Members();
//
//        members.set ;membersForm의 모든 get값을 members 셋값()안에 넣기
//
//       membersSVC.memUpdate(mid, members);
//        redirectAttributes.addAttribute("mid",members.getMid());
//        return "redirect:/mypage/{mid}/manager";
//        }
    }

//    회원 상제 정보 - 필요 없을지도, 회원 탈퇴, 타임리프로 값 받기