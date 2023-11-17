package com.example.demo.comment.entity;

import com.example.demo.answer.entity.Answer;
import com.example.demo.siteUser.entity.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  private LocalDateTime createDate;

  private LocalDateTime modifyDate;

  @ManyToOne
  private SiteUser author;

  @ManyToOne
  private Answer answer;
}
