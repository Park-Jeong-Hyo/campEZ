package com.campEZ.Project0.comments.dao;

import com.campEZ.Project0.entity.Comments;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class CommentsDAOImplTest {
  @Autowired
  CommentsDAO commentsDAO;

  //댓글 작성
  @Test
  @DisplayName("댓글 작성")
  void commentsSave() {
    Comments comments = new Comments();
    comments.setPnumber(1);
    comments.setNickname("장길동");
    comments.setCotext("댓글삭제가능");
    Comments result = commentsDAO.commentsSave(comments);
    int conumber = result.getConumber();
    System.out.println(comments);
    System.out.println(conumber);
    Assertions.assertThat(conumber).isPositive();
  }
  //댓글 수정
  //결과값으로 1이 리턴되면 성공, conumber 값은 db에 존재하는 conumber 설정
  @Test
  @DisplayName("댓글 수정")
  void commentsUpdate() {
    int conumber = 21;
    Comments comments = new Comments();
    comments.setCotext("수정테스트");
    int result = commentsDAO.commentsUpdate(conumber, comments);
    System.out.println(result);
    Assertions.assertThat(result).isEqualTo(1);
  }
  //수동적 댓글 삭제
  //결과값으로 양수가 리턴되면 성공
  @Test
  @DisplayName("수동적 댓글 삭제")
  void commentsDeletePassive() {
    int pnumber = 1;
    int result = commentsDAO.commentsDeletePassive(pnumber);
    System.out.println(result);
    Assertions.assertThat(result).isPositive();
  }
  //능동적 댓글 삭제
  //결과값으로 1이 리턴되면 성공 conumber 값은 db에 존재하는 conumber 설정
  @Test
  @DisplayName("능동적 댓글 삭제")
  void commentsDeleteActive() {
    int conumber = 25;
    int result = commentsDAO.commentsDeleteActive(conumber);
    System.out.println(result);
    Assertions.assertThat(result).isEqualTo(1);
  }
  //댓글 조회
  // db에 존재하는 conumber 설정
  @Test
  @DisplayName("댓글조회")
  void commentsDetail() {
    int conumber = 26;
    Optional<Comments> result = commentsDAO.commentsDetail(conumber);
    System.out.println(result);
    Assertions.assertThat(result.toString()).isNotEqualTo("Optional.empty");
  }
}
