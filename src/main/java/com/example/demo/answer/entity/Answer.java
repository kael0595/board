package com.example.demo.answer.entity;

import com.example.demo.comment.entity.Comment;
import com.example.demo.review.entity.Review;
import com.example.demo.siteUser.entity.SiteUser;
import com.example.demo.question.entity.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;

  @ManyToOne
  private Question question;

  @ManyToOne
  private Review review;

  @ManyToOne
  private SiteUser author;

  private LocalDateTime modifyDate;

  @ManyToMany
  Set<SiteUser> voter;

  @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
  private List<Comment> commentList;
}
