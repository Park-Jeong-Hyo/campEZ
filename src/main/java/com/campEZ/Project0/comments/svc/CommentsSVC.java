package com.campEZ.Project0.comments.svc;

import com.campEZ.Project0.entity.Comments;

import java.util.Optional;

public interface CommentsSVC {
  Comments commentsSave(Comments comments);
  int commentsUpdate (int conumber, Comments comments);
  int commentsDeletePassive (int pnumber);
  int commentsDeleteActive (int conumber);
  Optional<Comments> commentsDetail (int conumber);
}
