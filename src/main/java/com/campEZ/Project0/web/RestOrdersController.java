package com.campEZ.Project0.web;


import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.orders.svc.OrdersSVC;
import com.campEZ.Project0.web.rest.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class RestOrdersController {

  private final CampingSVC campingSVC;
  private final OrdersSVC ordersSVC;

  @GetMapping("/{cnumber}/{area}")
  public Response<Object> test(
      @PathVariable("cnumber") Integer cnumber,
      @PathVariable("area") Integer area
  ){
    Response<Object> response = null;
    Integer integer = ordersSVC.campingAreaChange(cnumber, area);
    log.info("integer={}",integer);

    response = Response.createRestResponse("00","성공", integer);

    return response;
  }

  // 캠핑장 삭제
  @GetMapping("/del/{cnumber}")
  public Response<Object> delOrders(
      @PathVariable("cnumber") int cnumber
  ) {
    Response<Object> res = null;
    boolean orderchk = ordersSVC.orderIsExist(cnumber);

    if (orderchk == false) {
      campingSVC.campareaDelete(cnumber);
      campingSVC.campingDelete(cnumber);
      res =  Response.createRestResponse  ("00", "삭제 가능", orderchk);
    } else {
      res =  Response.createRestResponse("99", "삭제 불가능", orderchk);
    }
    return res;
  }
}
