package com.campEZ.Project0.comments.svc;

import com.campEZ.Project0.entity.Comments;

import java.util.List;
import java.util.Optional;

public interface CommentsSVC {
  int commentsSave(Comments comments);
  int commentsUpdate (int conumber, Comments comments);
  int commentsDeletePassive (int pnumber);
  int commentsDeleteActive (int conumber);
  Optional<Comments> commentsDetail (int conumber);

  List<Comments> commentsAll(int pnumber);
}
