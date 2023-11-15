package com.example.demo.controller;

import com.example.demo.entity.SiteUser;
import com.example.demo.exception.DataNotFoundException.DataNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
