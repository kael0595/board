package com.example.demo.question.entity;

import com.example.demo.answer.entity.Answer;
import com.example.demo.genFile.entity.GenFile;
import com.example.demo.siteUser.entity.SiteUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 200)
  private String subject;

  @Column(columnDefinition = "TEXT")
  private String content;


  @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
  private List<Answer> answerList;

  @ManyToOne
  private SiteUser author;

  private LocalDateTime createDate;

  private LocalDateTime modifyDate;

  @ManyToMany
  Set<SiteUser> voter;

  private int viewCount = 0;

  private String category;

  @ElementCollection
  @CollectionTable(name = "question_filenames", joinColumns = @JoinColumn(name = "question_id"))
  @Column(columnDefinition = "VARCHAR(255)")
  private List<String> filenames;

  @ElementCollection
  @CollectionTable(name = "question_filePaths", joinColumns = @JoinColumn(name = "question_id"))
  @Column(columnDefinition = "VARCHAR(255)")
  private List<String> filePaths;
}
