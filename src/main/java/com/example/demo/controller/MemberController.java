package com.example.demo.controller;

import com.example.demo.dto.MemberJoinForm;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usr")
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/join")
  public String join(MemberJoinForm memberForm){
    return "/usr/join";
  }

  @PostMapping("/join")
  public String join(@Valid MemberJoinForm memberForm,
                     BindingResult bindingResult){
    if (bindingResult.hasErrors()){
      return "/usr/join";
    }
    if (!memberForm.getPassword1().equals(memberForm.getPassword2())){
      bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
      return "/usr/join";
    }
    try {
      memberService.join(memberForm.getUserName()
          , memberForm.getPassword1(), memberForm.getEmail());
    }catch(DataIntegrityViolationException e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
      return "/usr/join";
    }catch(Exception e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", e.getMessage());
      return "/usr/join";
    }
    return "redirect:/";
  }
}
