package com.campEZ.Project0.post.dao;

import com.campEZ.Project0.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PostDAOImplTest {
  @Autowired
  PostDAO postDAO;
  //게시글 목록
  @Test
  @DisplayName("게시글 목록")
  void PostList() {
    Post post = new Post();
    post.setPtype('n');

    List<Post> list = postDAO.postList();
    System.out.println(list);
    Assertions.assertThat(list).toString();
  }
  //게시글 작성
//  @Test
//  @DisplayName("게시글 작성")
//  void postSave() {
//    Post post = new Post();
//    post.setNickname("홍길");
//    post.setPtitle("테스트");
//    post.setPtext("반갑습니다221.");
//    post.setPtype('n');
//    Post result = postDAO.postSave(post);
//    int pnumber = result.getPnumber();
//    System.out.println(pnumber);
//    System.out.println(post);
//    Assertions.assertThat(pnumber).isPositive();
//  }
  //게시글 수정
  //postUpdate의 결과값 row가 1이 반환되면 성공
  @Test
  @DisplayName("게시글 수정")
  void postUpdate() {
    int pnumber = 2;
    Post post = new Post();
    post.setPtitle("게시글수정12");
    post.setPtext("게시글수정내용1");
    int result = postDAO.postUpdate(pnumber, post);
    System.out.println(result);
    Assertions.assertThat(result).isPositive();
  }
  //게시글 삭제
  //postDelete의 결과값 row가 1이 반환되면 성공
  @Test
  @DisplayName("게시글 삭제")
  void postDelte() {
    int pnumber = 3;
    int result = postDAO.postDelete(pnumber);
    System.out.println(result);
    Assertions.assertThat(result).isPositive();
  }
  //게시글 상세조회
  //결과값 콘솔에 출력, pnumber가 1이므로 Optional.empty가 될 수 없음.
  @Test
  @DisplayName("게시글 상세조회")
  void postDetail() {
    int pnumber = 1;
    Optional<Post> result = postDAO.postDetail(pnumber);
    System.out.println(result);
    Assertions.assertThat(result.toString()).isNotEqualTo("Optional.empty");
  }
}
