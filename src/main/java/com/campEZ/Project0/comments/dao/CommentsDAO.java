package com.campEZ.Project0.comments.dao;

import com.campEZ.Project0.entity.Comments;

import java.util.Optional;

public interface CommentsDAO {
  //댓글 작성
  Comments commentsSave(Comments comments);
  //댓글 수정
  int commentsUpdate (int conumber, Comments comments);
  //수동적 댓글 삭제(게시글이 삭제될 경우 게시글 내 댓글 전체 삭제)
  int commentsDeletePassive (int pnumber);
  //능동적 댓글 삭제(댓글 작성자가 댓글 삭제)
  int commentsDeleteActive (int conumber);
  //댓글 조회
  Optional<Comments> commentsDetail(int conumber);
}
