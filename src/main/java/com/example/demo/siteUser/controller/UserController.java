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
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

 private PasswordEncoder passwordEncoder;

  @GetMapping("/join")
  public String join(UserCreateForm userCreateForm) {
    return "/user/join";
  }

  @PostMapping("/join")
  public String userJoin(@Valid UserCreateForm userCreateForm,
                         @RequestParam("file") MultipartFile file) throws Exception{
    if (file.isEmpty()){
      RsData<SiteUser> joinRs = userService.join(userCreateForm);
      if (joinRs.isFail()){
        return "/commons/js";
      }
    } else {
      RsData<SiteUser> joinRs = userService.join(userCreateForm, file);
      if (joinRs.isFail()){
        return "redirect:/user/join?failMsg=" + Ut.url.encode(joinRs.getMsg());
      } else {
        mailController.sendEmail(userCreateForm.getUsername(),userCreateForm.getEmail());
      }
    }

    return "redirect:/";
  }

//  @PostMapping("/mailConfirm")
//  @ResponseBody
//  public RsData mailConfirm(String email){
//    return userService.mailConfirm(email);
//  }


  @PreAuthorize("isAnonymous()")
  @GetMapping("/checkUsernameDup")
  @ResponseBody
  public RsData<String> checkUsernameDup(
      @NotBlank @Length(min = 4) String username
  ) {
    return userService.checkUsernameDup(username);
  }

  @PreAuthorize("isAnonymous()")
  @GetMapping("/checkEmailDup")
  @ResponseBody
  public RsData<String> checkEmailDup(
      @NotBlank @Length(min = 4) String email
  ) {
    return userService.checkEmailDup(email);
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

  @PostMapping("/me/update")
  public String update(@RequestParam("password1") String password1,
                       @RequestParam("passowrd2") String password2,
                       Principal principal){

    String username = principal.getName();
    SiteUser siteUser = this.userService.getUser(username);

    if (!password1.equals(password2)){
      return "변경할 비밀번호와 비밀번호 확인이 일치하지 않습니다.";
    }
    siteUser.setPassword(passwordEncoder.encode(password1));
    userService.saveUser(siteUser);
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