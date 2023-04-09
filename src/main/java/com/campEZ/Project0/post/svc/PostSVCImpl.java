package com.campEZ.Project0.post.svc;

import com.campEZ.Project0.entity.Post;
import com.campEZ.Project0.post.dao.PostDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostSVCImpl implements PostSVC{

  private final PostDAO postDAO;
  @Override
  public List<Post> postList() {
    return postDAO.postList();
  }
  @Override
  public int postSave(Post post) {
    return postDAO.postSave(post);
  }
  @Override
  public int postUpdate(int pnumber, Post post) {
    return postDAO.postUpdate(pnumber, post);
  }
  @Override
  public int postDelete(int pnumber) {
    return postDAO.postDelete(pnumber);
  }
  @Override
  public Optional<Post> postDetail(int pnumber) {
    return postDAO.postDetail(pnumber);
  }
}
