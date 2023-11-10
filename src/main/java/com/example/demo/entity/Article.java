package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String subject;

  private String content;

  private LocalDateTime createDate;

  private LocalDateTime modifyDate;

  @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
  private List<Answer> answerList;
}
