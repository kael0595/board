package com.example.demo.controller;

import com.example.demo.dto.ReviewForm;
import com.example.demo.entity.Question;
import com.example.demo.entity.Review;
import com.example.demo.entity.SiteUser;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

  private final ReviewService reviewService;

  private final UserService userService;
  @GetMapping("/list")
  public String list(Model model){
    List<Review> reviewList = this.reviewService.getList();
    model.addAttribute("reviewList", reviewList);
    return "/review/list";
  }

  @GetMapping("/create")
  public String create(ReviewForm reviewForm){
    return "/review/create";
  }

  @PostMapping("/create")
  public String create(@Valid ReviewForm reviewForm,
                       BindingResult bindingResult,
                       Principal principal){
    if (bindingResult.hasErrors()){
      return "/review/create";
    }
    SiteUser user = this.userService.getUser(principal.getName());
    this.reviewService.create(user, reviewForm);
    return "redirect:/review/list";
  }

  @GetMapping("/detail/{id}")
  public String detail(@PathVariable("id") Integer id,
                       Model model){
    Review review = this.reviewService.getReview(id);
    this.reviewService.increaseViewCount(review);
    model.addAttribute("review", review);
    return "/review/detail";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable("id") Integer id,
                       Principal principal){
    Review review = this.reviewService.getReview(id);
    if (!review.getAuthor().getUsername().equals(principal.getName())){
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제할 권한이 없습니다.");
    }
    this.reviewService.delete(review);
    return "redirect:/";
  }

  @GetMapping("/modify/{id}")
  public String modify(ReviewForm reviewForm,
                       @PathVariable("id") Integer id,
                       Principal principal){
    Review review = this.reviewService.getReview(id);
    if (!review.getAuthor().getUsername().equals(principal.getName())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 권한이 없습니다.");
    }
    review.setSubject(reviewForm.getSubject());
    review.setContent(reviewForm.getContent());
    return "/review/create";
  }

  @PostMapping("/modify/{id}")
  public String modify(@Valid ReviewForm reviewForm,
                       @PathVariable("id") Integer id,
                       Principal principal,
                       BindingResult bindingResult){
    if (bindingResult.hasErrors()){
      return "/review/create";
    }
    Review review = this.reviewService.getReview(id);
    if (!review.getAuthor().getUsername().equals(principal.getName())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정할 권한이 없습니다.");
    }
    this.reviewService.modify(review, reviewForm.getSubject(), reviewForm.getContent());
    return String.format("redirect:/detail/%s", id);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/vote/{id}")
  public String reviewVote(Principal principal, @PathVariable("id") Integer id) {
    Review review = this.reviewService.getReview(id);
    SiteUser user = this.userService.getUser(principal.getName());
    this.reviewService.vote(review, user);
    return String.format("redirect:/review/detail/%s", id);
  }
}
