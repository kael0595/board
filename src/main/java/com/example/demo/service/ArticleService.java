package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private ArticleRepository articleRepository;

  public List<Article> getList() {
    return articleRepository.findAll();
  }
}
