package com.example.demo.review.service;

import com.example.demo.review.model.ReviewForm;
import com.example.demo.review.entity.Review;
import com.example.demo.siteUser.entity.SiteUser;
import com.example.demo.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;

  public List<Review> getList() {
    return this.reviewRepository.findAll();
  }

  public void create(SiteUser user, ReviewForm reviewForm) {
    Review review = new Review();
    review.setSubject(reviewForm.getSubject());
    review.setContent(reviewForm.getContent());
    review.setAuthor(user);
    review.setCreateDate(LocalDateTime.now());
    this.reviewRepository.save(review);
  }

  public Review getReview(Integer id) {
    return this.reviewRepository.findById(id);
  }

  public void delete(Review review) {
    this.reviewRepository.delete(review);
  }

  public void modify(Review review, String subject, String content) {
    review.setSubject(subject);
    review.setContent(content);
    review.setModifyDate(LocalDateTime.now());
    this.reviewRepository.save(review);
  }

  public void vote(Review review, SiteUser user) {
    review.getVoter().add(user);
    this.reviewRepository.save(review);
  }

  public void increaseViewCount(Review review) {
    review.setViewCount(review.getViewCount()+1);
    this.reviewRepository.save(review);
  }
}
