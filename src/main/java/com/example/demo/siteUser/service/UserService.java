package com.example.demo.siteUser.service;

import com.example.demo.base.RsData;
import com.example.demo.genFile.entity.GenFile;
import com.example.demo.genFile.service.GenFileService;
import com.example.demo.siteUser.entity.SiteUser;
import com.example.demo.exception.DataNotFoundException.DataNotFoundException;
import com.example.demo.siteUser.model.UserCreateForm;
import com.example.demo.siteUser.repository.UserRepository;
import com.example.demo.standard.util.Ut;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final GenFileService genFileService;

  @Value("${custom.genFileDirPath}")
  private String genFileDirPath;
  @Value("${custom.originPath}")
  private String originPath;

  @Transactional
  public RsData<SiteUser> join(UserCreateForm userCreateForm, MultipartFile file) throws Exception {
    if (findByUsername(userCreateForm.getUsername()).isPresent()){
      return RsData.of("F-1","%S(은)는 사용중인 아이디입니다.".formatted(userCreateForm.getUsername()));
    }

     GenFile genFile = this.genFileService.upload(file);

      SiteUser user = SiteUser.
          builder()
          .username(userCreateForm.getUsername())
          .password(passwordEncoder.encode(userCreateForm.getPassword1()))
          .email(userCreateForm.getEmail())
          .createDate(LocalDateTime.now())
          .filepath(genFile.getFilepath())
          .filename(genFile.getFilename())
          .build();
      user = userRepository.save(user);

        return RsData.of("S-1", "회원가입이 완료되었습니다.", user);
  }

  public RsData<SiteUser> join(UserCreateForm userCreateForm) {
    SiteUser user = SiteUser.builder()
        .username(userCreateForm.getUsername())
        .password(passwordEncoder.encode(userCreateForm.getPassword1()))
        .email(userCreateForm.getEmail())
        .createDate(LocalDateTime.now())
        .build();
    user = userRepository.save(user);

    return RsData.of("S-1", "회원가입이 완료되었습니다.", user);

  }

  private Optional<SiteUser> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public SiteUser getUser(String username) {
    Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
    if (siteUser.isPresent()) {
      return siteUser.get();
    } else {
      throw new DataNotFoundException("siteuser not found");
    }
  }

  public SiteUser getUserByEmail(String email) {
    Optional<SiteUser> user = this.userRepository.findByEmail(email);
    if (user.isPresent()){
      return user.get();
    } throw new DataNotFoundException("user not found");
  }

  public SiteUser getUserByEmailAndUsername(String email, String username) {
    Optional<SiteUser> user = this.userRepository.findByEmailAndUsername(email, username);
    if (user.isPresent()){
      return user.get();
    } throw new DataNotFoundException("user not found");
  }

  public String generateTempPassword() {
    String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder sb = new StringBuilder();

    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      int index = random.nextInt(characters.length());
      sb.append(characters.charAt(index));
    }

    return sb.toString();
  }

  public void updatePW(SiteUser user, String password1) {
    user.setPassword(password1);
    this.userRepository.save(user);
  }

  public int IDCheck(String username) {
   Optional<SiteUser> user = this.userRepository.findByUsername(username);
    if (user.isPresent()){
      int response = 1;
      return response;
    } else {
      int response = 0;
      return response;
    }
}

  public int EmailCheck(String email) {
    Optional<SiteUser> user = this.userRepository.findByEmail(email);
    if (user.isPresent()){
      int response = 1;
      return response;
    } else {
      int response = 0;
      return response;
    }
  }
  public List<SiteUser> getAll() {
    return this.userRepository.findAll();
  }


  public RsData checkUsernameDup(String username) {
    if (findByUsername(username).isPresent()) return RsData.of("F-1", "%s(은)는 사용중인 아이디입니다.".formatted(username));

    return RsData.of("S-1", "%s(은)는 사용 가능한 아이디입니다.".formatted(username));
  }


  public boolean userEmailCheck(String userEmail, String userName) {

    SiteUser user = userRepository.findUserByEmail(userEmail);
    if (user != null && user.getUsername().equals(userName)) {
      return true;
    } else {
      return false;
    }
  }

  public RsData<String> checkEmailDup(String email) {
    if (findByEmail(email).isPresent()) return RsData.of("F-1", "%s(은)는 사용중인 이메일입니다.".formatted(email));

    return RsData.of("S-1", "%s(은)는 사용 가능한 이메일 입니다.".formatted(email), email);
  }

  private Optional<SiteUser> findByEmail(String email) {
    return userRepository.findByUsername(email);

  }

  public void saveUser(SiteUser siteUser) {
    this.userRepository.save(siteUser);
  }

//  public RsData mailConfirm(String email) {
//
//  }
}
