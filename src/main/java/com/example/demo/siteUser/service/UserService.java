package com.example.demo.siteUser.service;

import com.example.demo.base.RsData;
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
  public RsData<SiteUser> join(UserCreateForm userCreateForm, MultipartFile file) throws IOException {
    if (findByUsername(userCreateForm.getUsername()).isPresent()){
      return RsData.of("F-1","%S(은)는 사용중인 아이디입니다.".formatted(userCreateForm.getUsername()));
    }

    String projectPath = genFileDirPath;

    UUID uuid = UUID.randomUUID();
    String fileName = uuid + "_" + file.getOriginalFilename();
    String filePath = originPath + fileName;

    File saveFile = new File(projectPath, fileName);
    file.transferTo(saveFile);

      SiteUser user = SiteUser.
          builder()
          .username(userCreateForm.getUsername())
          .password(passwordEncoder.encode(userCreateForm.getPassword1()))
          .email(userCreateForm.getEmail())
          .createDate(LocalDateTime.now())
          .filepath(filePath)
          .filename(fileName)
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
}
