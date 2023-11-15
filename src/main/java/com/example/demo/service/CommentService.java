package com.example.demo.service;

import com.example.demo.entity.Answer;
import com.example.demo.entity.Comment;
import com.example.demo.entity.SiteUser;
import com.example.demo.exception.DataNotFoundException.DataNotFoundException;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  public Comment create(Answer answer, String content, SiteUser user) {
    Comment comment = new Comment();
    comment.setContent(content);
    comment.setAnswer(answer);
    comment.setAuthor(user);
    comment.setCreateDate(LocalDateTime.now());
    this.commentRepository.save(comment);
    return comment;
  }

  public Comment getComment(Integer id) {
    Optional<Comment> comment = this.commentRepository.findById(id);
    if (comment.isPresent()){
      return comment.get();
    } throw new DataNotFoundException("comment not found");
  }

  public void delete(Comment comment) {
    this.commentRepository.delete(comment);
  }

  public void modify(Comment comment, String content) {
    comment.setContent(content);
    comment.setModifyDate(LocalDateTime.now());
    this.commentRepository.save(comment);
  }
}
