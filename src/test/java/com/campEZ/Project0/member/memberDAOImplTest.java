package com.campEZ.Project0.member;

import com.campEZ.Project0.members.dao.MembersDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class memberDAOImplTest {

  @Autowired
  MembersDAO membersDAO;

//  @Test
//  @DisplayName("이메일확인")
//  void findEmail(String email) {
//    email = "test1@naver.com";
//    boolean result = membersDAO.emailIsExist(email);
//    System.out.println(result);
//  }

  @Test
  @DisplayName("아이디확인")
  void findId(String id) {
    id = "test123";
    System.out.println(id);
    boolean result = membersDAO.isExist(id);
    System.out.println(result);
  }
}
