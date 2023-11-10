package com.example.demo.service;

import com.example.demo.entity.Answer;
import com.example.demo.entity.Article;
import com.example.demo.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

  private final AnswerRepository answerRepository;

  public void create(Article article, String content){
    Answer answer = new Answer();
    answer.setArticle(article);
    answer.setContent(content);
    answer.setCreateDate(LocalDateTime.now());
    this.answerRepository.save(answer);
  }
}
