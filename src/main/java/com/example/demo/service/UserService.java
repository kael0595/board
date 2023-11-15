package com.example.demo.service;

import com.example.demo.entity.SiteUser;
import com.example.demo.exception.DataNotFoundException.DataNotFoundException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
