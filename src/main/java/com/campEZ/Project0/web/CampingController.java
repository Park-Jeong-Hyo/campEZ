package com.campEZ.Project0.web;

import com.campEZ.Project0.camping.dao.CampingFilterCondition;
import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Camparea;
import com.campEZ.Project0.entity.Camping;
import com.campEZ.Project0.entity.UploadFile;
import com.campEZ.Project0.uploadfile.UploadFileSVC;
import com.campEZ.Project0.web.form.camping.CampingSaveForm;
import com.campEZ.Project0.web.form.camping.CampingSearchForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class CampingController {
  private final CampingSVC campingSVC;

  private final UploadFileSVC uploadFileSVC;

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
  //캠핑장 조회
  @GetMapping("{id}/detail")
  public String campingDetail(
      @PathVariable("id") int cnumber,
      Model model
  ) {
    CampingSaveForm campingSaveForm = new CampingSaveForm();
    Optional<Camping> detail = campingSVC.campingDetail(cnumber);
    Camping camping = detail.orElseThrow();
    campingSaveForm.setCnumber(camping.getCnumber());
    campingSaveForm.setMid(camping.getMid());
    campingSaveForm.setCname(camping.getCname());
    campingSaveForm.setCaddress(camping.getCaddress());
    campingSaveForm.setCamptel(camping.getCamptel());
    campingSaveForm.setCtype(camping.getCtype());
    campingSaveForm.setOperdate(camping.getOperdate());
    campingSaveForm.setHomepage(camping.getHomepage());
    campingSaveForm.setCtitle(camping.getCtitle());
    campingSaveForm.setCtext(camping.getCtext());
    campingSaveForm.setPriceweekday(camping.getPriceweekday());
    campingSaveForm.setPriceweekend(camping.getPriceweekend());
    campingSaveForm.setToilet(camping.getToilet());
    campingSaveForm.setMart(camping.getMart());
    campingSaveForm.setUdate(camping.getUdate());
    Optional<List<Camparea>> list = campingSVC.campareaDetail(camping.getCnumber());
    List<Camparea> campareaList = list.orElseThrow();

    // area 값은 해당 캠핑장이 가진 구역의 갯수를 뜻하고, 같은 cnumber를 공유하는 list객체의 경우 area의 값은 동일함.
    //따라서 임의의 값인 0~10사이의 아무 값이나 넣어 줘도 된다.
    campingSaveForm.setArea(campareaList.get(0).getArea());

    //리스트에서 구해온 capacitys의 값을 하나씩 넣어주는 작업
    // 이 부분에 값이 없을 경우 오류 발생, get(숫자).getCapacity()를 했을 때 해당하는 값이 있어야 한다.
    for (int i = 0; i < 10; i++) {
      if (i < campareaList.size()) {
        switch (i) {
          case 0:
            campingSaveForm.setCapacitys1(campareaList.get(i).getCapacitys());
            break;
          case 1:
            campingSaveForm.setCapacitys2(campareaList.get(i).getCapacitys());
            break;
          case 2:
            campingSaveForm.setCapacitys3(campareaList.get(i).getCapacitys());
            break;
          case 3:
            campingSaveForm.setCapacitys4(campareaList.get(i).getCapacitys());
            break;
          case 4:
            campingSaveForm.setCapacitys5(campareaList.get(i).getCapacitys());
            break;
          case 5:
            campingSaveForm.setCapacitys6(campareaList.get(i).getCapacitys());
            break;
          case 6:
            campingSaveForm.setCapacitys7(campareaList.get(i).getCapacitys());
            break;
          case 7:
            campingSaveForm.setCapacitys8(campareaList.get(i).getCapacitys());
            break;
          case 8:
            campingSaveForm.setCapacitys9(campareaList.get(i).getCapacitys());
            break;
          case 9:
            campingSaveForm.setCapacitys10(campareaList.get(i).getCapacitys());
            break;
        }
      }
    }

    log.info("campingSaveForm={}",campingSaveForm);

    //파일첨부조회
    List<UploadFile> imagedFile1 = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.A01, cnumber);
    List<UploadFile> imagedFile2 = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.A02, cnumber);
    List<UploadFile> imagedFiles1 = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.A03, cnumber);
    List<UploadFile> imagedFiles2 = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.A04, cnumber);

    if(!imagedFile1.isEmpty()){
      campingSaveForm.setImagedFile1(imagedFile1.get(0));
    }
    if(!imagedFile2.isEmpty()){
      campingSaveForm.setImagedFile2(imagedFile2.get(0));
    }
    if(!imagedFiles1.isEmpty()){
      campingSaveForm.setImagedFiles1(imagedFiles1);
    }
    if(!imagedFiles2.isEmpty()){
      campingSaveForm.setImagedFiles2(imagedFiles2);
    }

    model.addAttribute("campingSaveForm", campingSaveForm);
    return "detailPage/detailPage-user";

  }
  //캠핑장 등록화면 처리
  @GetMapping("/save")
  public String campingsaveForm(Model model, HttpSession session) {
    CampingSaveForm campingSaveForm = new CampingSaveForm();
    model.addAttribute("campingSaveForm", campingSaveForm);
    return "detailPage/detailPageSave";
  }
  //캠핑장 등록
  @PostMapping("/save")
  public String campingSave(
      @Valid @ModelAttribute CampingSaveForm campingSaveForm,
      BindingResult bindingResult,
      HttpSession session,
      RedirectAttributes redirectAttributes
  ) {
    //유효성 검사
    if(bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
    }
    //세션에서 mid를 가져온다.
    LoginMembers loginMembers = (LoginMembers)session.getAttribute(SessionConst.LOGIN_MEMBER);
    String mid = loginMembers.getMid();
    Camping camping = new Camping();
    Camparea camparea = new Camparea();

    //캠핑장 정보 입력 로직
    camping.setMid(mid);
    camping.setCname(campingSaveForm.getCname());
    camping.setCaddress(campingSaveForm.getCaddress());
    camping.setCamptel(campingSaveForm.getCamptel());
    camping.setCtype(campingSaveForm.getCtype());
    camping.setOperdate(campingSaveForm.getOperdate());
    camping.setHomepage(campingSaveForm.getHomepage());
    camping.setCtitle(campingSaveForm.getCtitle());
    camping.setCtext(campingSaveForm.getCtext());
    camping.setPriceweekday(campingSaveForm.getPriceweekday());
    camping.setPriceweekend(campingSaveForm.getPriceweekend());
    camping.setToilet(campingSaveForm.getToilet());
    camping.setMart(campingSaveForm.getMart());

    //camparea에 들어갈 cnumber를 가져오기 위함
    Camping result = campingSVC.campingSave(camping);

    //캠핑장 구역 정보 입력 로직
    camparea.setCnumber(result.getCnumber());
    int maxListNumber = campingSaveForm.getArea();
    log.info("maxListNumber={}", maxListNumber);
    List<Integer> areaList = new ArrayList<>();
    for(int i = 0; i < maxListNumber ; i++ ){
      areaList.add(i + 1);
    }
    //반복문을 사용하여 camparea를 마지막까지 인서트하는 로직
    List<Integer> capacitysList = new ArrayList<>();
    for(int i = 0; i < maxListNumber ; i++) {
      switch(i) {
        case 0 :
          capacitysList.add(campingSaveForm.getCapacitys1());
          break;
        case 1 :
          capacitysList.add(campingSaveForm.getCapacitys2());
          break;
        case 2 :
          capacitysList.add(campingSaveForm.getCapacitys3());
          break;
        case 3 :
          capacitysList.add(campingSaveForm.getCapacitys4());
          break;
        case 4 :
          capacitysList.add(campingSaveForm.getCapacitys5());
          break;
        case 5 :
          capacitysList.add(campingSaveForm.getCapacitys6());
          break;
        case 6 :
          capacitysList.add(campingSaveForm.getCapacitys7());
          break;
        case 7 :
          capacitysList.add(campingSaveForm.getCapacitys8());
          break;
        case 8 :
          capacitysList.add(campingSaveForm.getCapacitys9());
          break;
        case 9 :
          capacitysList.add(campingSaveForm.getCapacitys10());
          break;
      }
    }
      log.info("capacitys={}",capacitysList);
      // area와 capacitys를 인서트
      for (int i = 0 ; i < maxListNumber ; i++) {
        if(capacitysList.get(i) != null) {
          camparea.setArea(areaList.get(i));
          camparea.setCapacitys(capacitysList.get(i));
          campingSVC.campareaSave(camparea);
        }
      }
    //완료후 리다이렉트 하기 위함.
    int id = result.getCnumber();
    redirectAttributes.addAttribute("id", id);
    return "redirect:/search/{id}/detail";
  }
}
