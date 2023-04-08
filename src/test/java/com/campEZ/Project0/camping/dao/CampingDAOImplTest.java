package com.campEZ.Project0.camping.dao;

import com.campEZ.Project0.camping.svc.CampingSVC;
import com.campEZ.Project0.entity.Camparea;
import com.campEZ.Project0.entity.Camping;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

@SpringBootTest
public class CampingDAOImplTest {
  @Autowired
  CampingDAO campingDAO;

  //camparea의 cnumber와 camping의 cnumber가 같으면 성공
  //addresss는 unique값이므로 테스트 할때마다 다시 설정해 주어야 함.
  @Test
  @DisplayName("캠핑장 등록, 캠핑 지역 등록")
  void campingEnroll() {
    Camping camping = new Camping();
    Camparea camparea = new Camparea();
    camping.setMid("test124");
    camping.setCname("테스트캠핑장1");
    camping.setCaddress("테스트118");
    camping.setCamptel("010-1234-1234");
    camping.setCtype('a');
    camping.setOperdate("09:00~21:00");
    camping.setHomepage("https:github.com");
    camping.setCtitle("테스트캠핑");
    camping.setCtext("테스트텍스트");
    camping.setPriceweekday(30000);
    camping.setPriceweekend(40000);
    camping.setToilet('o');
    camping.setMart('x');
    camparea.setArea(4);
    camparea.setCapacitys(5);
    campingDAO.campingSave(camping, camparea);
    Assertions.assertThat(camping.getCnumber()).isEqualTo(camparea.getCnumber());
  }
  @Test
  @DisplayName("캠핑장 수정, 캠핑구역 수정")
  //수정된 열 갯수 1이 출력되면 성공
  void campingUpdate() {
  Camping camping = new Camping();
  Camparea camparea = new Camparea();
  int cnumber = 1;
    camping.setCname("수정캠핑장");
    camping.setCaddress("수정주소");
    camping.setCamptel("010-1234-1234");
    camping.setCtype('a');
    camping.setOperdate("09:00~21:00");
    camping.setHomepage("https:github.com");
    camping.setCtitle("수정타이틀");
    camping.setCtext("수정텍스트");
    camping.setPriceweekday(30000);
    camping.setPriceweekend(40000);
    camping.setToilet('o');
    camping.setMart('x');
    camparea.setCapacitys(5);
    camparea.setArea(2);
    int result = campingDAO.campingUpdate(camping, camparea, cnumber);
    System.out.println(camping);
    System.out.println(camparea);
    System.out.println(result);
    Assertions.assertThat(result).isEqualTo(1);
  }
  
  //삭제된 열 갯수 1이 출력되면 성공
  @Test
  @DisplayName("캠핑장 삭제")
  void campingDelete() {
    int cnumber = 1;
    int result = campingDAO.campingDelete(cnumber);
    System.out.println(result);
    Assertions.assertThat(result).isEqualTo(1);
  }

  //쿼리 검색결과가 나오면 성공
  @Test
  @DisplayName("캠핑장 검색")
  void campingSearch() {
    CampingFilterCondition filterCondition = new CampingFilterCondition();
    filterCondition.setCampingType("a");
    filterCondition.setCampingRegion("울산");
    filterCondition.setCampingKeyword("KH");
    List<Camping> list = campingDAO.campingSearch(filterCondition);
    System.out.println(list);
  }
}
