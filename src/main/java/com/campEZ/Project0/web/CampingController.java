package com.campEZ.Project0.web;

import com.campEZ.Project0.camping.dao.CampingFilterCondition;
import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Camparea;
import com.campEZ.Project0.entity.Camping;
import com.campEZ.Project0.entity.UploadFile;
import com.campEZ.Project0.uploadfile.UploadFileDAO;
import com.campEZ.Project0.uploadfile.UploadFileSVC;
import com.campEZ.Project0.web.form.camping.CampingDetailForm;
import com.campEZ.Project0.web.form.camping.CampingSaveForm;
import com.campEZ.Project0.web.form.camping.CampingSearchForm;
import com.campEZ.Project0.web.form.camping.CampingUpdateForm;
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
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class CampingController {
  private final CampingSVC campingSVC;

  private final UploadFileSVC uploadFileSVC;

  private final UploadFileDAO uploadFileDAO;

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
    if (bindingResult.hasErrors()) {
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

    //최대 구역 값을 구하기 위한 로직 (손대지 말 것)
    CampingSearchForm areaForm = new CampingSearchForm();
    int maxNumber = 0;
    for(int i = 0; i < list.size() ; i++) {
      List<Camparea> compareList = campingSVC.campareaDetail(list.get(i).getCnumber()).orElseThrow();
      for(int j = 0; j < compareList.size() ; j++ ) {
        if(maxNumber < compareList.get(j).getArea()) {
          maxNumber = compareList.get(j).getArea();
          areaForm.setArea(maxNumber);
        };
      };
    };

    //list 내부의 Camping객체를 순환하는 for
    for (Camping camping : list) {
      //list내부의 정보를 담을 CampingSearchform생성
      CampingSearchForm campingSearchForm1 = new CampingSearchForm();
      //camping 객체의 필드를, campingSearchForm에 있는 같은 이름의 필드에 복사하는 BeanUtils.copyProperties
      BeanUtils.copyProperties(camping, campingSearchForm1);
      //List<CampSearchForm>에 저장
      completeList.add(campingSearchForm1);
    }

    model.addAttribute("completeList", completeList);
    model.addAttribute("areaForm", areaForm);
    log.info("completelist={}", completeList);
    return "search/SearchListMain";
  }

  //캠핑장 조회
  @GetMapping("{id}/{areaNumber}/detail")
  public String campingDetail(
      @PathVariable("id") int cnumber,
      @PathVariable("areaNumber") int areaNumber,
      Model model,
      HttpSession session,
      RedirectAttributes redirectAttributes
  ) {
    LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);
    CampingDetailForm campingDetailForm = new CampingDetailForm();
    Optional<Camping> detail = campingSVC.campingDetail(cnumber);
    Camping camping = detail.orElseThrow();

    // 캠핑장을 등록한 사람이 자신의 캠핑장을 조회할 경우 수정, 삭제페이지로 넘어간다.
    try {
      if (loginMembers.getMid().equalsIgnoreCase(camping.getMid())) {
        log.info("사업자가 본인이 등록한 캠핑장에 접속");
        redirectAttributes.addAttribute("id", cnumber);
        redirectAttributes.addAttribute("areaNumber", areaNumber);
        return "redirect:/search/{id}/{areaNumber}/update";
      }
    } catch (NullPointerException e) {
      log.info("비회원 조회페이지에 접속");
    }
    campingDetailForm.setCnumber(camping.getCnumber());
    campingDetailForm.setMid(camping.getMid());
    campingDetailForm.setCname(camping.getCname());
    campingDetailForm.setCaddress(camping.getCaddress());
    campingDetailForm.setCamptel(camping.getCamptel());
    campingDetailForm.setCtype(camping.getCtype());
    campingDetailForm.setOperdate(camping.getOperdate());
    campingDetailForm.setHomepage(camping.getHomepage());
    campingDetailForm.setCtext(camping.getCtext());
    campingDetailForm.setPriceweekday(camping.getPriceweekday());
    campingDetailForm.setPriceweekend(camping.getPriceweekend());
    campingDetailForm.setToilet(camping.getToilet());
    campingDetailForm.setMart(camping.getMart());
    campingDetailForm.setUdate(camping.getUdate());
    Optional<List<Camparea>> list = campingSVC.campareaDetail(camping.getCnumber());
    List<Camparea> campareaList = list.orElseThrow();

    // area 값은 해당 캠핑장이 가진 구역의 갯수를 뜻하고, 같은 cnumber를 공유하는 list객체의 경우 area의 값은 동일함.
    //따라서 임의의 값인 0~10사이의 아무 값이나 넣어 줘도 된다.
    campingDetailForm.setArea(campareaList.get(0).getArea());

    //리스트에서 구해온 capacitys의 값을 하나씩 넣어주는 작업
    // 이 부분에 값이 없을 경우 오류 발생, get(숫자).getCapacity()를 했을 때 해당하는 값이 있어야 한다.
    int isAreaNumberTrue = 0;
    for (int i = 0; i < 10; i++) {
      if (i < campareaList.size()) {
        switch (i) {
          case 0:
            campingDetailForm.setCapacitys1(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 1:
            campingDetailForm.setCapacitys2(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 2:
            campingDetailForm.setCapacitys3(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 3:
            campingDetailForm.setCapacitys4(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 4:
            campingDetailForm.setCapacitys5(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 5:
            campingDetailForm.setCapacitys6(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 6:
            campingDetailForm.setCapacitys7(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 7:
            campingDetailForm.setCapacitys8(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 8:
            campingDetailForm.setCapacitys9(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 9:
            campingDetailForm.setCapacitys10(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
        }
      }
    }
    if(isAreaNumberTrue != areaNumber) {
      return "error/404";
    }

    log.info("campingDetailForm={}", campingDetailForm);
    //파일첨부조회
    List<UploadFile> imagedFile = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.A01, cnumber);
    List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.A02, cnumber);

    if(!imagedFile.isEmpty()){
      campingDetailForm.setImagedFile(imagedFile.get(0));
    }
    if(!imagedFiles.isEmpty()){
      campingDetailForm.setImagedFiles(imagedFiles);
    }

    model.addAttribute("campingDetailForm", campingDetailForm);
    return "detailPage/detailPage-user";

  }

  //캠핑장 등록화면 처리
  @GetMapping("/save")
  public String campingsaveForm(Model model, HttpSession session) {
    LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);
    //사업자인지 아닌지 확인하는 로직, 아닐 경우 메인페이지로
    try {
      if (loginMembers.getMtype().equalsIgnoreCase("b")) {
        log.info("사업자입니다.");
        CampingSaveForm campingSaveForm = new CampingSaveForm();
        model.addAttribute("campingSaveForm", campingSaveForm);
        return "detailPage/detailPageSave";
      }
    } catch (NullPointerException e) {
      log.info("사업자가 아닙니다.");
      return "redirect:/";
    }
    return "redirect:/";
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
    camping.setCtext(campingSaveForm.getCtext());
    camping.setPriceweekday(campingSaveForm.getPriceweekday());
    camping.setPriceweekend(campingSaveForm.getPriceweekend());
    camping.setToilet(campingSaveForm.getToilet());
    camping.setMart(campingSaveForm.getMart());

    //파일첨부에 대한 메타정보추출 & 물리파일 저장
    UploadFile imageFile = uploadFileSVC.convert(campingSaveForm.getImageFile(), AttachFileType.A01);
    List<UploadFile> imageFiles = uploadFileSVC.convert(campingSaveForm.getImageFiles(), AttachFileType.A02);
    System.out.println(imageFiles);
    if(imageFile != null) imageFiles.add(imageFile);

    //camparea에 들어갈 cnumber를 가져오기 위함
    Camping result;

    //캠핑장 저장
    if(imageFiles.isEmpty()){
      result = campingSVC.campingSave(camping);
    }else{
      result = campingSVC.campingSave(camping, imageFiles);
    }

    //캠핑장 구역 정보 입력 로직
    camparea.setCnumber(result.getCnumber());
    int maxListNumber = campingSaveForm.getArea();
    log.info("maxListNumber={}", maxListNumber);
    //수정 전 캠핑장 구역 숫자
    int areaNumber = campingSaveForm.getArea();
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
    redirectAttributes.addAttribute("mid", mid);
    return "redirect:/mypage/{mid}/manager";
  }

  //캠핑장 수정화면
  @GetMapping("/{id}/{areaNumber}/update")
  public String campingUpdateForm(
      Model model,
      @PathVariable("id") int cnumber,
      @PathVariable("areaNumber") int areaNumber,
      HttpSession session) {
    LoginMembers loginMembers = (LoginMembers)session.getAttribute(SessionConst.LOGIN_MEMBER);
    Optional<Camping> detail = campingSVC.campingDetail(cnumber);
    Camping camping = detail.orElseThrow();
    // 캠핑장을 등록한 사람이 자신의 캠핑장을 조회할 경우 수정, 삭제페이지로 넘어간다.
    try {
      if(!(loginMembers.getMid().equalsIgnoreCase(camping.getMid()))) {
        log.info("잘못된 접근");
        return "redirect:/";
      }
    } catch(NullPointerException e) {
      log.info("잘못된 접근");
      return "redirect:/";
    }
    CampingUpdateForm campingUpdateForm = new CampingUpdateForm();
    campingUpdateForm.setCnumber(camping.getCnumber());
    campingUpdateForm.setMid(camping.getMid());
    campingUpdateForm.setCname(camping.getCname());
    campingUpdateForm.setCaddress(camping.getCaddress());
    campingUpdateForm.setCamptel(camping.getCamptel());
    campingUpdateForm.setCtype(camping.getCtype());
    campingUpdateForm.setOperdate(camping.getOperdate());
    campingUpdateForm.setHomepage(camping.getHomepage());
    campingUpdateForm.setCtext(camping.getCtext());
    campingUpdateForm.setPriceweekday(camping.getPriceweekday());
    campingUpdateForm.setPriceweekend(camping.getPriceweekend());
    campingUpdateForm.setToilet(camping.getToilet());
    campingUpdateForm.setMart(camping.getMart());
    campingUpdateForm.setUdate(camping.getUdate());
    campingUpdateForm.setCnumber(cnumber);
    campingUpdateForm.setAreaNumber(areaNumber);
    Optional<List<Camparea>> list = campingSVC.campareaDetail(camping.getCnumber());
    List<Camparea> campareaList = list.orElseThrow();

    //파일첨부조회
    List<UploadFile> imagedFile = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.A01, cnumber);
    List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.A02, cnumber);

    if(!imagedFile.isEmpty()){
      campingUpdateForm.setImagedFile(imagedFile.get(0));
    }
    if(!imagedFiles.isEmpty()){
      campingUpdateForm.setImagedFiles(imagedFiles);
    }

    // area 값은 해당 캠핑장이 가진 구역의 갯수를 뜻하고, 같은 cnumber를 공유하는 list객체의 경우 area의 값은 동일함.
    //따라서 임의의 값인 0~10사이의 아무 값이나 넣어 줘도 된다.
    campingUpdateForm.setArea(campareaList.get(0).getArea());

    //리스트에서 구해온 capacitys의 값을 하나씩 넣어주는 작업
    // 이 부분에 값이 없을 경우 오류 발생, get(숫자).getCapacity()를 했을 때 해당하는 값이 있어야 한다.
    //url위조 방지용 코드, url에 최대 camparea 계산에 필요한 정보가 들어가 있으므로 거짓으로 입력되면 안된다.
    int isAreaNumberTrue = 0;
    for (int i = 0; i < 10; i++) {
      if (i < campareaList.size()) {
        switch (i) {
          case 0:
            campingUpdateForm.setCapacitys1(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 1:
            campingUpdateForm.setCapacitys2(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 2:
            campingUpdateForm.setCapacitys3(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 3:
            campingUpdateForm.setCapacitys4(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 4:
            campingUpdateForm.setCapacitys5(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 5:
            campingUpdateForm.setCapacitys6(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 6:
            campingUpdateForm.setCapacitys7(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 7:
            campingUpdateForm.setCapacitys8(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 8:
            campingUpdateForm.setCapacitys9(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
          case 9:
            campingUpdateForm.setCapacitys10(campareaList.get(i).getCapacitys());
            if(campareaList.get(i).getArea() > isAreaNumberTrue) {
              isAreaNumberTrue = campareaList.get(i).getArea();
            }
            break;
        }
      }
    }
    if(isAreaNumberTrue != areaNumber) {
      return "error/404";
    }
    model.addAttribute("campingUpdateForm", campingUpdateForm);
    return ("detailPage/detailPageUpdate");
  }

  //캠핑장 수정
  @PostMapping("/update")
  public String campingUpdate(
      @Valid @ModelAttribute CampingSaveForm campingSaveForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      HttpSession session
  ) {
    //마이페이지로 redirect하기 위함
    LoginMembers loginMembers = (LoginMembers)session.getAttribute(SessionConst.LOGIN_MEMBER);
    String mid = loginMembers.getMid();

    int cnumber = campingSaveForm.getCnumber();
    if(bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
    }
    Camping camping = new Camping();
    Camparea camparea = new Camparea();
    camping.setCname(campingSaveForm.getCname());
    camping.setCaddress(campingSaveForm.getCaddress());
    camping.setCamptel(campingSaveForm.getCamptel());
    camping.setCtype(campingSaveForm.getCtype());
    camping.setOperdate(campingSaveForm.getOperdate());
    camping.setHomepage(campingSaveForm.getHomepage());
    camping.setCtext(campingSaveForm.getCtext());
    camping.setPriceweekday(campingSaveForm.getPriceweekday());
    camping.setPriceweekend(campingSaveForm.getPriceweekend());
    camping.setToilet(campingSaveForm.getToilet());
    camping.setMart(campingSaveForm.getMart());
    campingSVC.campingUpdate(camping, cnumber);

    log.info("cnumber={}", cnumber);
    camparea.setCnumber(cnumber);
    camparea.setArea(campingSaveForm.getArea());

    // 기존 이미지 물리파일 삭제
    List<UploadFile> uploadFiles = uploadFileSVC.findFilesByRid(cnumber);
    List<String> files = uploadFiles.stream().map(file -> file.getStorename()).collect(Collectors.toList());
    uploadFileSVC.deleteCampFiles(files);

    // 메타정보삭제
    uploadFileDAO.deleteFileByRid(cnumber);

    // 파일첨부에 대한 메타정보추출 & 물리파일 저장
    UploadFile imageFile = uploadFileSVC.convert(campingSaveForm.getImageFile(), AttachFileType.A01);
    List<UploadFile> imageFiles = uploadFileSVC.convert(campingSaveForm.getImageFiles(), AttachFileType.A02);
    System.out.println(imageFiles);
    if(imageFile != null) imageFiles.add(imageFile);

    // 첨부파일 메타정보 저장
    if(!(imageFiles.isEmpty())) {
      uploadFileDAO.addFiles(imageFiles);
    }

    //캠핑장 구역 정보 입력 로직
    //수정된 값(maxListNumber)
    int maxListNumber = campingSaveForm.getArea();
    //이전의 캠핑장 구역 갯수
    int areaNumber = campingSaveForm.getAreaNumber();
    // 수정된 maxListNumber(area) 보다 큰 값을 제거
    for(int i = 10 ; i > maxListNumber ; i--) {
      campingSVC.campareaDelete(i);
    }

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
    //기존 구역값 보다 수정된 구역 값이 더 클때
    if(maxListNumber > areaNumber) {
      //우선은 기존 구역 값까지는 업데이트
      for (int i = 0 ; i < areaNumber ; i++) {
        if(capacitysList.get(i) != null) {
          camparea.setCnumber(cnumber);
          camparea.setArea(areaList.get(i));
          camparea.setCapacitys(capacitysList.get(i));
          campingSVC.campareaUpdate(camparea);
        }
      }
      //그 이상의 구역 값에 대해서 추가하는 로직
      //5
      //maxListNumber(수정값) - areaNumber(기존값) = 차이값, 차이값 만큼 인서트
      for(int i = areaNumber ; i < maxListNumber ; i++) {
        if(capacitysList.get(i) != null) {
          camparea.setCnumber(cnumber);
          camparea.setArea(areaList.get(i));
          camparea.setCapacitys(capacitysList.get(i));
          campingSVC.campareaSave(camparea);
        }
      }
    }
    //기존 구역값(areaNumber)이 수정된 구역 값(maxListNumber)보다 같거나 클 때
    if(maxListNumber <= areaNumber) {
      for (int i = 0 ; i < maxListNumber ; i++) {
        if(capacitysList.get(i) != null) {
          camparea.setCnumber(cnumber);
          camparea.setArea(areaList.get(i));
          camparea.setCapacitys(capacitysList.get(i));
          campingSVC.campareaUpdate(camparea);
        }
      }
    }
    //코드의 통일성을 위해 id에 할당
    int id = cnumber;
    //완료후 리다이렉트
    //areaNumber을 수정된 값(maxListNumber)로 업데이트
    redirectAttributes.addAttribute("mid", mid);
    return "redirect:/mypage/{mid}/manager";
  }
}