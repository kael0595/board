package com.example.demo.review.entity;

import com.example.demo.answer.entity.Answer;
import com.example.demo.siteUser.entity.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String subject;

  private String content;

  private LocalDateTime createDate;

  private LocalDateTime modifyDate;

  @ManyToOne
  private SiteUser author;

  @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
  private List<Answer> answerList;

  private int viewCount = 0;

  @ManyToMany
  Set<SiteUser> voter;
}
