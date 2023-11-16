package com.example.demo.controller;

import com.example.demo.dto.CommentForm;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Comment;
import com.example.demo.entity.SiteUser;
import com.example.demo.service.AnswerService;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

  private final CommentService commentService;
  private final UserService userService;
  private final AnswerService answerService;

  @GetMapping("/create/answer/{id}")
  public String createComment(CommentForm commentForm){
    return "/comment/create";
  }

  @PostMapping("/create/answer/{id}")
  public String createComment(Model model,
                       @PathVariable("id") Integer id,
                       @Valid CommentForm commentForm,
                       BindingResult bindingResult,
                       Principal principal){
    Answer answer = this.answerService.getAnswer(id);
    SiteUser user = this.userService.getUser(principal.getName());
    if (bindingResult.hasErrors()){
      model.addAttribute("answer", answer);
      return "/question/detail";
    }
    Comment comment = this.commentService.create(answer, commentForm.getContent(), user);
    return String.format("redirect:/question/detail/%s#comment_%s",
        comment.getAnswer().getQuestion().getId(), comment.getAnswer().getId());
  }

  @GetMapping("/modify/{id}")
  public String commentModify(CommentForm commentForm,
                              @PathVariable("id") Integer id,
                              Principal principal){
    Comment comment = this.commentService.getComment(id);
    if (!comment.getAuthor().getUsername().equals(principal.getName())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
    }
    commentForm.setContent(comment.getContent());
    return "/comment/create";
  }

  @PostMapping("/modify/{id}")
  public String commentModify(@Valid CommentForm commentForm,
                              BindingResult bindingResult,
                              @PathVariable("id") Integer id,
                              Principal principal){
    if (bindingResult.hasErrors()){
      return "/comment/create";
    }
    Comment comment = this.commentService.getComment(id);
    if (!comment.getAuthor().getUsername().equals(principal.getName())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
    }
    this.commentService.modify(comment, commentForm.getContent());
    return String.format("redirect:/question/detail/%s#comment_%s",
        comment.getAnswer().getQuestion().getId(), comment.getAnswer().getId()); }

  @GetMapping("/delete/{id}")
  public String commentDelete(@PathVariable("id") Integer id,
                       Principal principal){
    Comment comment = this.commentService.getComment(id);
    if (!comment.getAuthor().getUsername().equals(principal.getName())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    }
    this.commentService.delete(comment);
    return String.format("redirect:/question/detail/%s#comment_%s",
        comment.getAnswer().getQuestion().getId(), comment.getAnswer().getId());  }
}
