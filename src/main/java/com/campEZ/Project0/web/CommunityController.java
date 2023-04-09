package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.Post;
import com.campEZ.Project0.form.post.PostDetailForm;
import com.campEZ.Project0.form.post.PostSaveForm;
import com.campEZ.Project0.form.post.PostUpdateForm;
import com.campEZ.Project0.post.svc.PostSVC;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

  private final PostSVC postSVC;

  // 공지사항 목록 맵핑
  @GetMapping("/notice")
  public String notice(Model model) {

    List<Post> posts = postSVC.postList();
    model.addAttribute("posts",posts);
    return "community/notice";
  }

  // 자유게시판 목록 맵핑
  @GetMapping("/butlletinBoard")
  public String butlletinBoard(Model model) {
    List<Post> posts = postSVC.postList();
    model.addAttribute("posts",posts);
    return "community/butlletinBoard";
  }

  // 자유 게시글 작성양식
  @GetMapping("/write")
  public String writeForm(Model model) {
    PostSaveForm postSaveForm = new PostSaveForm();
    model.addAttribute("postSaveForm",postSaveForm);
    return "community/butlletinBoardPosting";
  }

  // 자유 게시글 작성처리
  @PostMapping("/write")
  public String g_write(
      @Valid @ModelAttribute PostSaveForm postsaveform,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    log.info("postsaveform={}",postsaveform);

    Post post = new Post();
    post.setPtype('f');
    post.setNickname(postsaveform.getNickname());
    post.setPtitle(postsaveform.getPtitle());
    post.setPtext(postsaveform.getPtext());
    post.setUdate(postsaveform.getUdate());

    log.info("post={}",post);
    int savedPnumber = postSVC.postSave(post);
    redirectAttributes.addAttribute("id",savedPnumber);
    log.info("savedPnumber={}",savedPnumber);
    return "redirect:/community/{id}/g_read";
  }

  // 게시글 수정양식
  @GetMapping("/{id}/edit")
  public String updateForm(
      @PathVariable("id") int pnumber,
      Model model
  ){
    Optional<Post> findedPost = postSVC.postDetail(pnumber);
    Post post = findedPost.orElseThrow();

    PostUpdateForm postUpdateForm = new PostUpdateForm();
    postUpdateForm.setPnumber(post.getPnumber());
    postUpdateForm.setNickname(post.getNickname());
    postUpdateForm.setPtitle(post.getPtitle());
    postUpdateForm.setPtext(post.getPtext());
    postUpdateForm.setPtype(post.getPtype());
    postUpdateForm.setUdate(post.getUdate());

    model.addAttribute("postUpdateForm",postUpdateForm);
    log.info("postUpdateForm={}",postUpdateForm);
    return "community/updatePost";
  }

  // 게시글 수정
  @PostMapping("/{id}/edit")
  public String update(
      @PathVariable("id") int pnumber,
      @Valid @ModelAttribute PostUpdateForm postUpdateForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ){

    //정상 처리
    Post post = new Post();
    post.setPtitle(postUpdateForm.getPtitle());
    post.setPtext(postUpdateForm.getPtext());
    post.setUdate(postUpdateForm.getUdate());
    postSVC.postUpdate(pnumber, post);

    redirectAttributes.addAttribute("id",pnumber);
    return "redirect:/community/{id}/g_read";
  }

  // 게시글 삭제
  @GetMapping("/{id}/del")
  public String deleteById(@PathVariable("id") int pnumber){

    postSVC.postDelete(pnumber);

    return "redirect:/community/butlletinBoard";
  }
  // 자유 게시글 조회
  @GetMapping("/{id}/g_read")
  public String general_read(
      @PathVariable("id") int id, Model model
  ) {
    Optional<Post> readPost = postSVC.postDetail(id);
    Post post = readPost.orElseThrow();

    PostDetailForm postDetailForm = new PostDetailForm();
    postDetailForm.setPnumber(post.getPnumber());
    postDetailForm.setNickname(post.getNickname());
    postDetailForm.setPtitle(post.getPtitle());
    postDetailForm.setPtext(post.getPtext());
    postDetailForm.setPtype(post.getPtype());
    postDetailForm.setUdate(post.getUdate());

    model.addAttribute("postDetailForm",postDetailForm);
    return "community/generalPost";
  }

  // 공지사항 조회
  @GetMapping("/{id}/n_read")
  public String notice_read(
      @PathVariable("id") int id, Model model
  ) {
    Optional<Post> readPost = postSVC.postDetail(id);
    Post post = readPost.orElseThrow();

    PostDetailForm postDetailForm = new PostDetailForm();
    postDetailForm.setPnumber(post.getPnumber());
    postDetailForm.setNickname(post.getNickname());
    postDetailForm.setPtitle(post.getPtitle());
    postDetailForm.setPtext(post.getPtext());
    postDetailForm.setPtype(post.getPtype());
    postDetailForm.setUdate(post.getUdate());

    model.addAttribute("postDetailForm",postDetailForm);
    return "community/noticePost";
  }

  // 댓글 작성

  // 댓글 수정

  // 댓글 삭제

  // 댓글 조회
}