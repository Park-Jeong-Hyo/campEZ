package com.campEZ.Project0.web;

import com.campEZ.Project0.camping.dao.CampingFilterCondition;
import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Camping;
import com.campEZ.Project0.web.form.camping.CampingSearchForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class CampingController {
  private final CampingSVC campingSVC;

  // 캠핑장 화면 연결
  @GetMapping
  public String campingForm(@ModelAttribute("campingSearchForm") CampingSearchForm campingForm) {
    return "search/SearchListMain";
  }

   //캠핑장 검색
  @PostMapping
  public String campingSearch(
      @Valid @ModelAttribute CampingSearchForm campingSearchForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      Model model
  ) {
    List<Camping> list = null;
    // valid 검사
    if(bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "search/SearchListMain";
    }
    CampingFilterCondition filterCondition = new CampingFilterCondition();
    filterCondition.setCampingKeyword(campingSearchForm.getCname());
    filterCondition.setCampingType(campingSearchForm.getCtype());
    filterCondition.setCampingRegion(campingSearchForm.getCaddress());
    log.info("filterCondition={}", filterCondition);

    list = campingSVC.campingSearch(filterCondition);
    log.info("list={}", list);

    List<CampingSearchForm> completeList = new ArrayList<>();

    //list 내부의 Camping객체를 순환하는 forEach
    for(Camping camping : list) {
      //list내부의 정보를 담을 CampingSearchform생성
      CampingSearchForm campingSearchForm1 = new CampingSearchForm();
      //camping 객체의 필드를, campingSearchForm에 있는 같은 이름의 필드에 복사하는 BeanUtils.copyProperties
      BeanUtils.copyProperties(camping, campingSearchForm1);
      //List<CampSearchForm>에 저장
      completeList.add(campingSearchForm1);
    }
    model.addAttribute("completeList", completeList);
    log.info("completelist={}", completeList);
    return "search/SearchListMain";
  }
}
