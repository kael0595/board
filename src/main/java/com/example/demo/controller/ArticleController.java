package com.example.demo.controller;

import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    model.addAttribute("articleList",articleList);
    return "/article/list";
  }

  @GetMapping("/create")
  public String create(ArticleForm articleForm){
    return "/article/create";
  }

  @PostMapping("/create")
  public String create(ArticleForm articleForm,
                       BindingResult bindingResult){
    if (bindingResult.hasErrors()){
      return "/article/create";
    }
    this.articleService.create(articleForm.getSubject(), articleForm.getContent());
    return "redirect:/article/list";
  }

  @GetMapping(value = "/detail/{id}")
  public String detail(Model model,
                       @PathVariable("id") Long id){
    Article article = this.articleService.getArticle(id);
    model.addAttribute("article", article);
    return "/article/detail";
  }
}
