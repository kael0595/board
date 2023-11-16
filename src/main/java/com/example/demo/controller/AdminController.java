package com.example.demo.controller;

import com.example.demo.entity.SiteUser;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final UserService userService;

  @GetMapping("/")
  public String adminRoot(Model model){
    List<SiteUser> userList = this.userService.getList();
    model.addAttribute("userList", userList);
    return "/admin/home";
  }
}
