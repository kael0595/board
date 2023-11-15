package com.example.demo.controller;

import com.example.demo.dto.UserCreateForm;
import com.example.demo.entity.SiteUser;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  private final MailController mailController;

  @GetMapping("/join")
  public String signup(UserCreateForm userCreateForm) {
    return "/user/join";
  }

  @PostMapping("/join")
  public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/user/join";
    }

    if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
      bindingResult.rejectValue("password2", "passwordInCorrect",
          "2개의 패스워드가 일치하지 않습니다.");
      return "/user/join";
    }

    try {
      userService.create(userCreateForm.getUsername(),
          userCreateForm.getEmail(), userCreateForm.getPassword1());
    }catch(DataIntegrityViolationException e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
      return "/user/join";
    }catch(Exception e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", e.getMessage());
      return "/user/join";
    }

    return "redirect:/";
  }

  @GetMapping("/login")
  public String login() {
    return "/user/login";
  }

  @GetMapping("/find")
  public String find(){
  return "/user/find";
  }

  @PostMapping("/findID")
  @ResponseBody
  public String findID(@RequestParam("email") String email){
    SiteUser user = this.userService.getUserByEmail(email);
    if (user != null){
      mailController.sendEmailForID(email, user.getUsername());
      return "true";
    } else {
      return "redirect:/user/find";
    }
  }

  @PostMapping("/findPW")
  @ResponseBody
  public String findPW(@RequestParam("email") String email, @RequestParam("username") String username){
    SiteUser user = this.userService.getUserByEmailAndUsername(email, username);
    if (user != null){
      mailController.sendEmailForPW(email, username);
      return "true";
    } else {
      return "redirect:/user/find";
    }
  }

}