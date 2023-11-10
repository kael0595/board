package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.service.AnswerService;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

  private final AnswerService answerService;

  private final ArticleService articleService;


  @PostMapping("/create/{id}")
  private String create(Model model,
                        @PathVariable("id") Long id,
                        @RequestParam String content){
    Article article = this.articleService.getArticle(id);
    this.answerService.create(article, content);
    return String.format("redirect:/article/detail/%s", id);
  }
}
