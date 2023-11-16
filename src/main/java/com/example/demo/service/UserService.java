package com.example.demo.service;

import com.example.demo.entity.SiteUser;
import com.example.demo.exception.DataNotFoundException.DataNotFoundException;
import com.example.demo.repository.UserRepository;
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

  public SiteUser create(String username, String email, String password) {
    SiteUser user = new SiteUser();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setCreateDate(LocalDateTime.now());
    this.userRepository.save(user);
    return user;
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

  public List<SiteUser> getList() {
    List<SiteUser> userList = this.userRepository.findAll();
    return userList;
  }
}
