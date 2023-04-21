package com.campEZ.Project0.post.dao;
import com.campEZ.Project0.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostDAO {
  //게시글 전체 목록
  List<Post> postList(int Page, char ptype);
  //게시글 작성
  int postSave(Post post);
  //게시글 수정
  int postUpdate(int pnumber, Post post);
  //게시글 삭제
  int postDelete(int pnumber);
  //게시글 상세 조회
  Optional<Post> postDetail(int pnumber);
  // 최대 페이지 수
  int Count(char ptype);
}
