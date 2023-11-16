package com.example.demo.controller;

import com.example.demo.entity.SiteUser;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final UserService userService;

  @GetMapping("/home")
  public String adminRoot(Model model){
    List<SiteUser> userList = this.userService.getAll();
    model.addAttribute("userList", userList);
    log.info("userList: {}", userList);
    return "/admin/home";
  }
}
