package com.example.demo.review.repository;

import com.example.demo.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  Review findById(Integer id);
}
