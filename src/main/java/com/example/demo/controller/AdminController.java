package com.example.demo.controller;

import com.example.demo.dto.CategoryForm;
import com.example.demo.entity.SiteUser;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final UserService userService;

  private final CategoryService categoryService;


  @GetMapping("/home")
  public String adminRoot(Model model){
    List<SiteUser> userList = this.userService.getAll();
    model.addAttribute("userList", userList);
    log.info("userList: {}", userList);
    return "/admin/home";
  }

  @GetMapping("/category/create")
  public String create(CategoryForm categoryForm){
    return "/category/create";
  }

  @PostMapping("/category/create")
  public String create(@Valid CategoryForm categoryForm,
                       BindingResult bindingResult,
                       Principal principal){
    if (bindingResult.hasErrors()){
      return "/category/create";
    }
    this.categoryService.create(categoryForm.getName());
    return "redirect:/admin/home";
  }
}
