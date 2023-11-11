package com.example.demo.controller;

import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

  private final ArticleService articleService;

  @GetMapping("/list")
  public String list(Model model,
                     @RequestParam(value = "page", defaultValue = "0") int page){
    Page<Article> paging = this.articleService.getList(page);
    model.addAttribute("paging",paging);
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
