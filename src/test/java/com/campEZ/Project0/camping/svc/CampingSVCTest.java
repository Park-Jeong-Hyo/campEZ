package com.campEZ.Project0.camping.svc;

import com.campEZ.Project0.entity.Camping;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class CampingSVCTest {
@Autowired
CampingSVC campingSVC;
  @Test
  void campingFindByManagerMid() {
    List<Camping> campingList = campingSVC.campingFindByManagerMid("test123");
    for (Camping camping : campingList) {
      log.info("Camping cname: {}", camping.getCname());
    }
  }
}