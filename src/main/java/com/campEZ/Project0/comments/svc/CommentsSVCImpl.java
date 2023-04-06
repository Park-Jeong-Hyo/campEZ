package com.campEZ.Project0.comments.svc;

import com.campEZ.Project0.comments.dao.CommentsDAO;
import com.campEZ.Project0.entity.Comments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsSVCImpl implements CommentsSVC {

  private final CommentsDAO commentsDAO;

  @Override
  public Comments commentsSave(Comments comments) {
    return commentsDAO.commentsSave(comments);
  }

  @Override
  public int commentsUpdate(int conumber, Comments comments) {
    return commentsDAO.commentsUpdate(conumber, comments);
  }

  @Override
  public int commentsDeletePassive(int pnumber) {
    return commentsDAO.commentsDeletePassive(pnumber);
  }

  @Override
  public int commentsDeleteActive(int conumber) {
    return commentsDAO.commentsDeleteActive(conumber);
  }

  @Override
  public Optional<Comments> commentsDetail(int conumber) {
    return commentsDAO.commentsDetail(conumber);
  }
}
