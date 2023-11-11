package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  private final PasswordEncoder passwordEncoder;

  public void join(String userName, String password, String email){
    Member member = new Member();
    member.setUserName(userName);
    member.setPassword(passwordEncoder.encode(password));
    member.setEmail(email);
    member.setCreateDate(LocalDateTime.now());
    this.memberRepository.save(member);
  }
}
