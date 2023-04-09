package com.campEZ.Project0.post.svc;
import com.campEZ.Project0.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostSVC {
  List<Post> postList();
  int postSave(Post post);
  int postUpdate(int pnumber, Post post);
  int postDelete(int pnumber);
  Optional<Post> postDetail(int pnumber);
}
