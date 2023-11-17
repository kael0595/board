package com.example.demo.siteUser.service;

import com.example.demo.base.RsData;
import com.example.demo.siteUser.entity.SiteUser;
import com.example.demo.exception.DataNotFoundException.DataNotFoundException;
import com.example.demo.siteUser.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public RsData<SiteUser> join(String username, String email, String password) {
    if (findByUsername(username).isPresent()){
      return RsData.of("F-1","%S(은)는 사용중인 아이디입니다.".formatted(username));
    }
    SiteUser user = SiteUser.
        builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .email(email)
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
}
