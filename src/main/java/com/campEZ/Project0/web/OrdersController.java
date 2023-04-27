package com.campEZ.Project0.web;


import com.campEZ.Project0.entity.Orders;
import com.campEZ.Project0.orders.svc.OrdersSVC;
import com.campEZ.Project0.web.form.orders.OrdersForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

  private final OrdersSVC ordersSVC;


  //예약화면
  @GetMapping("/{cnumber}") //캠핑장상세페이지에서 예약페이지로 캠핑장번호 가져오기
  public String ordersForm(
      //data-cnumber받기
      @PathVariable("cnumber") Integer cnumber, //캠핑장상세페이지에서 예약페이지로 캠핑장번호 가져오기
      Model model,
      HttpSession session //세션 가져오기
      ){
    //세션 가져오기
    LoginMembers loginMembers = (LoginMembers)session.getAttribute(SessionConst.LOGIN_MEMBER);
    log.info("loginMembers={}",loginMembers);
    if ( loginMembers == null) {
      return "redirect:/login";
    }
    OrdersForm ordersForm = new OrdersForm();

    //아이디 가져오기
    ordersForm.setMid(loginMembers.getMid());

    //캠핑장상세페이지에서 예약페이지로 캠핑장번호 가져오기
    ordersForm.setCnumber(cnumber);

    //캠핑장 번호로 구역정보 가져오기
    List<Integer> reserAreas = ordersSVC.campingFindByCnumber(cnumber);
    log.info("reserAreas={}",reserAreas.size());

    //캠핑테이블에서 캠핑장 번호로 소개글 가져오기
    String campingCtext = ordersSVC.campingCtext(cnumber);
    log.info("campingCtext={}",campingCtext);

    //구역정보 모델에 저장하기
    model.addAttribute("reserAreas",reserAreas);

    //캠핑테이블에서 캠핑장 번호로 소개글 가져와 모델에 저장하기
    model.addAttribute("campingCtext",campingCtext);

    model.addAttribute("ordersForm", ordersForm);

    log.info("model={}",model);
    return "orders/orders";
  }



  //예약처리
  @PostMapping("/{cnumber}")
  public String order(
      @Valid @ModelAttribute("ordersForm") OrdersForm ordersForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      Model model,
      @PathVariable("cnumber") Integer cnumber
  ){

    //유효성 검사
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "/orders/orders";
    }

    //입실일이 퇴실일보다 클 때 제약조건 정의
    if(ordersForm.getCheckin().compareTo(ordersForm.getCheckout()) == 1){
      model.addAttribute("dateEr1","퇴실일이 입실일보다 커야 합니다");
      return "/orders/orders";
    }

    LocalDate now = LocalDate.now();
    //입실일이 과거 날짜보다 작을때 제약조건 정의
    if(ordersForm.getCheckin().compareTo(String.valueOf(now)) == -1 ){
      model.addAttribute("dateEr2","현재 날짜나 과거 날짜에 예약 불가능 합니다");
      return "/orders/orders";
    }

    //입실일이 현재 날짜와 같을때 제약조건 정의
    if(ordersForm.getCheckin().compareTo(String.valueOf(now)) == 0 ) {
      model.addAttribute("dateEr3", "현재 날짜나 과거 날짜에 예약 불가능 합니다");
      return "/orders/orders";
    }

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "/orders/orders";
    }

    //등록
    Orders orders = new Orders();
    orders.setCnumber(ordersForm.getCnumber());
    orders.setArea(ordersForm.getArea());
    orders.setMid(ordersForm.getMid());
    orders.setPhone(ordersForm.getPhone());
    orders.setHeadcount(ordersForm.getHeadcount());
    orders.setCheckin(ordersForm.getCheckin());
    orders.setCheckout(ordersForm.getCheckout());

    Orders savedOnumber = ordersSVC.order(orders);
    int onumber = Integer.valueOf(savedOnumber.getOnumber());
    redirectAttributes.addAttribute("id", onumber);

    log.info("redirectAttributes={}",redirectAttributes);

    //캠핑테이블에서 캠핑장번호로 이름 가져오기
    String campingCname = ordersSVC.campingCname(cnumber);
    model.addAttribute("campingCname",campingCname);

    return "orders/ordersCheck";
  }

}
