package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SiteUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  @Column(unique = true)
  private String email;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Question> questions;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Answer> answers;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  private LocalDateTime createDate;

  private LocalDateTime updateDate;
}
