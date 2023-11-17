package com.example.demo.comment.repository;

import com.example.demo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  Optional<Comment> findById(Integer id);
}
