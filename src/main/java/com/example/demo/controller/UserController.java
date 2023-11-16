package com.example.demo.controller;

import com.example.demo.dto.UserCreateForm;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Question;
import com.example.demo.entity.SiteUser;
import com.example.demo.service.AnswerService;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  private final MailController mailController;

  private final QuestionService questionService;

  private final AnswerService answerService;

  private final CommentService commentService;

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
  public String findID(@RequestParam("userEmail") String email){
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
  public String findPW(@RequestParam("userEmail ") String email, @RequestParam("userName") String userName){
    SiteUser user = this.userService.getUserByEmailAndUsername(email, userName);
    if (user != null){
      mailController.sendEmailForPW(email, userName);
      return "true";
    } else {
      return "redirect:/user/find";
    }
  }

  @GetMapping("/me")
  public String me(Model model,
                   Principal principal){
    SiteUser user = this.userService.getUser(principal.getName());
    List<Question> questionList = user.getQuestions();
    List<Answer> answerList = user.getAnswers();
    List<Comment> commentList = user.getComments();
    model.addAttribute("user", user);
    model.addAttribute("questionList",questionList);
    model.addAttribute("answerList", answerList);
    model.addAttribute("commentList", commentList);
    return "/user/me";
  }

  @PostMapping("/updatePW")
  @ResponseBody
  public String updatePW(Model model,
                         UserCreateForm createForm,
                         Principal principal){
    SiteUser user = this.userService.getUser(principal.getName());
    this.userService.updatePW(user, createForm.getPassword1());
    return "true";
  }
  @PostMapping("/checkUsername")
  @ResponseBody
  public int checkUsername(@RequestParam("username") String username){
    int response = userService.IDCheck(username);
    return response;
  }

  @PostMapping("/checkEmail")
  @ResponseBody
  public int checkEmail(@RequestParam("email") String email){
    int response = userService.EmailCheck(email);
    return response;
  }
}