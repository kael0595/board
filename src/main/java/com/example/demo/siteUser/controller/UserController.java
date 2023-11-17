package com.example.demo.siteUser.controller;

import com.example.demo.base.RsData;
import com.example.demo.mail.controller.MailController;
import com.example.demo.siteUser.model.UserCreateForm;
import com.example.demo.answer.entity.Answer;
import com.example.demo.comment.entity.Comment;
import com.example.demo.question.entity.Question;
import com.example.demo.siteUser.entity.SiteUser;
import com.example.demo.answer.service.AnswerService;
import com.example.demo.comment.service.CommentService;
import com.example.demo.question.service.QuestionService;
import com.example.demo.siteUser.service.UserService;
import com.example.demo.standard.util.Ut;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
  public String join(UserCreateForm userCreateForm) {
    return "/user/join";
  }

  @PostMapping("/join")
  public String userJoin(@Valid UserCreateForm userCreateForm) {
    RsData<SiteUser> joinRs = userService.join(userCreateForm.getUsername(), userCreateForm.getPassword1(),
                                                userCreateForm.getEmail(), userCreateForm.getProfileImgPath());
    if (joinRs.isFail()){
      return "redirect:/user/join?failMsg=" + Ut.url.encode(joinRs.getMsg());
    } else {
      mailController.sendEmail(userCreateForm.getUsername(),userCreateForm.getEmail());
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