package com.campEZ.Project0.web;

import com.campEZ.Project0.entity.Post;
import com.campEZ.Project0.post.svc.PostSVC;
import com.campEZ.Project0.web.form.post.PostDetailForm;
import com.campEZ.Project0.web.form.post.PostSaveForm;
import com.campEZ.Project0.web.form.post.PostUpdateForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

  // 자유게시판 목록 맵핑
  @GetMapping("/bulletinBoard")
  public String bulletinBoard(Model model) {
    List<Post> posts = postSVC.postList();
    model.addAttribute("posts",posts);
    return "community/bulletinBoard";
  }

  // 자유 게시글 작성양식
  @GetMapping("/write/bb")
  public String b_writeForm(
      Model model,
      HttpSession session
  ) {

    LoginMembers loginMembers = (LoginMembers)session.getAttribute(SessionConst.LOGIN_MEMBER);

    PostSaveForm postSaveForm = new PostSaveForm();
    postSaveForm.setNickname(loginMembers.getNickname());
    model.addAttribute("postSaveForm",postSaveForm);
    return "community/bulletinBoardPosting";
  }

  // 자유 게시글 작성처리
  @PostMapping("/write/bb")
  public String b_write(
      @Valid @ModelAttribute PostSaveForm postsaveform,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      HttpSession session
  ) {
    log.info("postsaveform={}",postsaveform);

    Post post = new Post();
    BeanUtils.copyProperties(postsaveform, post);

    LoginMembers loginMembers = (LoginMembers)session.getAttribute(SessionConst.LOGIN_MEMBER);

    post.setPtype('f');
    post.setNickname(loginMembers.getNickname());
    post.setPtitle(postsaveform.getPtitle());
    post.setPtext(postsaveform.getPtext());
    post.setUdate(postsaveform.getUdate());

    log.info("post={}",post);
    int savedPnumber = postSVC.postSave(post);
    redirectAttributes.addAttribute("id",savedPnumber);
    log.info("savedPnumber={}",savedPnumber);
    return "redirect:/community/{id}/b_read";
  }

  // 자유 게시글 수정양식
  @GetMapping("/{id}/edit")
  public String b_updateForm(
      @PathVariable("id") int pnumber,
      Model model,
      HttpSession session
  ){
    // 데이터 객체에 담기
    Optional<Post> findedPost = postSVC.postDetail(pnumber);
    Post post = findedPost.orElseThrow();

    // 로그인 객체
    LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);

    // 객체 닉네임 String 타입 변수로 저장
    String postNickname = String.valueOf(post.getNickname());
    String loginNickname = String.valueOf(loginMembers.getNickname());

    // equals로 저장한 변수 비교 true 값일 시 수정페이지로 이동
    if (postNickname.equals(loginNickname)) {

      PostUpdateForm postUpdateForm = new PostUpdateForm();
      postUpdateForm.setPnumber(post.getPnumber());
      postUpdateForm.setNickname(post.getNickname());
      postUpdateForm.setPtitle(post.getPtitle());
      postUpdateForm.setPtext(post.getPtext());
      postUpdateForm.setPtype(post.getPtype());
      postUpdateForm.setUdate(post.getUdate());

      model.addAttribute("postUpdateForm", postUpdateForm);
      log.info("postUpdateForm={}", postUpdateForm);
      return "community/updateBPost";
    } else {
      return "redirect:/community/bulletinBoard";
    }
  }

  // 자유 게시글 수정
  @PostMapping("/{id}/edit")
  public String b_update(
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
    return "redirect:/community/{id}/b_read";
  }

  // 게시글 삭제
  @GetMapping("/{id}/b_del")
  public String b_deleteById(
      @PathVariable("id") int pnumber,
      Model model,
      HttpSession session
  ){

    // 데이터 객체에 담기
    Optional<Post> findedPost = postSVC.postDetail(pnumber);
    Post post = findedPost.orElseThrow();

    // 로그인 객체
    LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);

    // 객체 닉네임 String 타입 변수로 저장
    String postNickname = String.valueOf(post.getNickname());
    String loginNickname = String.valueOf(loginMembers.getNickname());

    if ( postNickname.equals(loginNickname) ) {
      postSVC.postDelete(pnumber);

      return "redirect:/community/bulletinBoard";
    } else {
      return "redirect:/community/bulletinBoard";
    }
  }

  // 자유 게시글 조회
  @GetMapping("/{id}/b_read")
  public String b_read(
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
    return "community/bPost";
  }

  // 질문게시판 목록 맵핑
  @GetMapping("/question")
  public String notice(Model model) {

    List<Post> posts = postSVC.postList();
    model.addAttribute("posts",posts);
    return "community/question";
  }

  // 질문 게시글 작성양식
  @GetMapping("/write/qst")
  public String q_writeForm(
      Model model,
      HttpSession session
  ) {

    LoginMembers loginMembers = (LoginMembers)session.getAttribute(SessionConst.LOGIN_MEMBER);

    PostSaveForm postSaveForm = new PostSaveForm();
    postSaveForm.setNickname(loginMembers.getNickname());
    model.addAttribute("postSaveForm",postSaveForm);
    return "community/QuestionPosting";
  }

  // 질문 게시글 작성처리
  @PostMapping("/write/qst")
  public String q_write(
      @Valid @ModelAttribute PostSaveForm postsaveform,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      HttpSession session
  ) {
    log.info("postsaveform={}",postsaveform);

    Post post = new Post();
    BeanUtils.copyProperties(postsaveform, post);

    LoginMembers loginMembers = (LoginMembers)session.getAttribute(SessionConst.LOGIN_MEMBER);

    post.setPtype('n');
    post.setNickname(loginMembers.getNickname());
    post.setPtitle(postsaveform.getPtitle());
    post.setPtext(postsaveform.getPtext());
    post.setUdate(postsaveform.getUdate());

    log.info("post={}",post);
    int savedPnumber = postSVC.postSave(post);
    redirectAttributes.addAttribute("id",savedPnumber);
    log.info("savedPnumber={}",savedPnumber);
    return "redirect:/community/{id}/q_read";
  }

  // 질문 게시글 수정양식
  @GetMapping("/{id}/q_edit")
  public String q_UpdateForm(
      @PathVariable("id") int pnumber,
      Model model,
      HttpSession session
  ) {
    // 데이터 객체에 담기
    Optional<Post> findedPost = postSVC.postDetail(pnumber);
    Post post = findedPost.orElseThrow();

    // 로그인 객체
    LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);

    // 객체 닉네임 String 타입 변수로 저장
    String postNickname = String.valueOf(post.getNickname());
    String loginNickname = String.valueOf(loginMembers.getNickname());

    // equals로 저장한 변수 비교 true 값일 시 수정페이지로 이동
    if (postNickname.equals(loginNickname)) {

      PostUpdateForm postUpdateForm = new PostUpdateForm();
      postUpdateForm.setPnumber(post.getPnumber());
      postUpdateForm.setNickname(post.getNickname());
      postUpdateForm.setPtitle(post.getPtitle());
      postUpdateForm.setPtext(post.getPtext());
      postUpdateForm.setPtype(post.getPtype());
      postUpdateForm.setUdate(post.getUdate());

      model.addAttribute("postUpdateForm", postUpdateForm);
      log.info("postUpdateForm={}", postUpdateForm);
      return "community/updateQPost";
    } else {
      return "redirect:/community/question";
    }
  }
  // 질문 게시글 수정
  @PostMapping("/{id}/q_edit")
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
    return "redirect:/community/{id}/q_read";
  }

  // 질문 게시글 삭제
  @GetMapping("/{id}/q_del")
  public String q_deleteById(
      @PathVariable("id") int pnumber,
      Model model,
      HttpSession session
  ){

    // 데이터 객체에 담기
    Optional<Post> findedPost = postSVC.postDetail(pnumber);
    Post post = findedPost.orElseThrow();

    // 로그인 객체
    LoginMembers loginMembers = (LoginMembers) session.getAttribute(SessionConst.LOGIN_MEMBER);

    // 객체 닉네임 String 타입 변수로 저장
    String postNickname = String.valueOf(post.getNickname());
    String loginNickname = String.valueOf(loginMembers.getNickname());

    if ( postNickname.equals(loginNickname) ) {
      postSVC.postDelete(pnumber);

      return "redirect:/community/question";
    } else {
      return "redirect:/community/question";
    }
  }

  // 질문 게시글 조회
  @GetMapping("/{id}/q_read")
  public String q_read(
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
    return "community/qPost";
  }

  // 댓글 작성

  // 댓글 수정

  // 댓글 삭제

  // 댓글 조회
}