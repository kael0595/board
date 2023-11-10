package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;

  public List<Article> getList() {
    return articleRepository.findAll();
  }

  public void create(String subject, String content) {

    Article article = new Article();
    article.setSubject(subject);
    article.setContent(content);
    article.setCreateDate(LocalDateTime.now());
    this.articleRepository.save(article);
  }
}
