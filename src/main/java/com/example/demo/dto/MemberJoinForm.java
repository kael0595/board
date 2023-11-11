package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinForm {

  private String userName;

  private String password1;

  private String password2;

  @Email
  private String email;

}
