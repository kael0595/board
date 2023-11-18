package com.example.demo.mail.controller;

import com.example.demo.siteUser.entity.SiteUser;
import com.example.demo.exception.DataNotFoundException.DataNotFoundException;
import com.example.demo.siteUser.repository.UserRepository;
import com.example.demo.siteUser.service.UserService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

  private final JavaMailSender mailSender;

  private final UserService userService;

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @PostMapping("/user/findID/sendEmail")
  public void sendEmailForID(@RequestParam("email") String email, String username){
    String from = "admin@sbb.com";//보내는 이 메일주소
    String to = email;
    String title = "아이디 찾기 결과입니다.";
    String content = "[아이디] " + username + " 입니다. <br/>";
    try {
      MimeMessage mail = mailSender.createMimeMessage();
      MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

      mailHelper.setFrom(from);
      mailHelper.setTo(to);
      mailHelper.setSubject(title);
      mailHelper.setText(content, true);

      mailSender.send(mail);

    } catch (Exception e) {
      throw new DataNotFoundException("error");
    }
  }

  @PostMapping("/user/findPW/sendEmail")
  public void sendEmailForPW(@RequestParam("email") String email, String username) {

    String tempPW = userService.generateTempPassword();
    String from = "admin@sbb.com";
    String to = email;
    String title = "임시 비밀번호입니다.";
    String content = username + "님의 [임시비밀번호]는 " + tempPW + "입니다. <br/> 접속한 후 비밀번호를 변경해주세요";
    try {
      MimeMessage mail = mailSender.createMimeMessage();
      MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

      mailHelper.setFrom(from);
      mailHelper.setTo(to);
      mailHelper.setSubject(title);
      mailHelper.setText(content, true);

      mailSender.send(mail);

      SiteUser user = userService.getUserByEmailAndUsername(email, username);
      user.setPassword(passwordEncoder.encode(tempPW));
      userRepository.save(user);

    } catch (Exception e) {
      throw new DataNotFoundException("error");
    }
  }

  @PostMapping("/user/sendEmail")
  public void sendEmail(String username, String email) {
    String from = "admin@sbb.com";//보내는 이 메일주소
    String to = email;
    String title = "회원가입을 환영합니다.!";
    String content = username + "님의 회원가입을 환영합니다. <br/>";
    try {
      MimeMessage mail = mailSender.createMimeMessage();
      MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

      mailHelper.setFrom(from);
      mailHelper.setTo(to);
      mailHelper.setSubject(title);
      mailHelper.setText(content, true);

      mailSender.send(mail);

    } catch (Exception e) {
      throw new DataNotFoundException("error");
    }
  }

  @GetMapping("/mailCheck")
  @ResponseBody
  public int processMailCheck(@RequestParam("email") String email) throws Exception {
    int mailKey = (int) ((Math.random() * (99999 - 10000 + 1)) + 10000);

    String from = "admin@ToolTool.com";//보내는 이 메일주소
    String to = email;
    String title = "회원가입시 필요한 인증번호 입니다.";
    String content = "[인증번호] " + mailKey + " 입니다. <br/> 인증번호 확인란에 기입해주십시오.";
    try {
      MimeMessage mail = mailSender.createMimeMessage();
      MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

      mailHelper.setFrom(from);
      mailHelper.setTo(to);
      mailHelper.setSubject(title);
      mailHelper.setText(content, true);

      mailSender.send(mail);

    } catch (Exception e) {
      throw new DataNotFoundException("error");
    }
    return mailKey;
  }
}
