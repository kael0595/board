package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Member {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String userName;

  private String password;

  @Column(unique = true)
  private String email;

  private LocalDateTime createDate;

  private LocalDateTime modifyDate;
}
