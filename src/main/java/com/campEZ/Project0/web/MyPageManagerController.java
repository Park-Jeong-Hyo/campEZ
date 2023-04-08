package com.campEZ.Project0.web;

import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Camping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageManagerController {
    private final CampingSVC campingSVC;
//    캠핑 불러오기
//    @GetMapping("/myPage_common")
//    public String Commonlist(Model model,검색조건 검색조건){
//        List<Camping> list = campingSVC.전체찾는메소드(검색조건);
//        List<Camping> allList = new ArrayList<>();
//        for (Camping camping : list) {
//            Camping listForm = new Camping();
//            BeanUtils.copyProperties(camping, listForm);
//            allList.add(listForm);
//        }
//
//    model.addAttribute("list", allList);
//
//    return "myPage/myPage_common";
//
//    }
}
