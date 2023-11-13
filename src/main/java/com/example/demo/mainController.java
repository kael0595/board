package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {

  @GetMapping("/sbb")
  public String index(){
    return "/home/main";
  }

  @GetMapping("/")
  public String root(){return "redirect:/question/list";}
}
