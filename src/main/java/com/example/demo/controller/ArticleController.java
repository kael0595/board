package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

  private final ArticleService articleService;

  @GetMapping("/list")
  public String list(Model model){
    List<Article> articleList = this.articleService.getList();
    return "/article/list";
  }
}
