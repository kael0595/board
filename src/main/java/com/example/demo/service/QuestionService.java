package com.example.demo.service;

import com.example.demo.dto.QuestionForm;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;
import com.example.demo.entity.SiteUser;
import com.example.demo.exception.DataNotFoundException.DataNotFoundException;
import com.example.demo.repository.QuestionRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;

  private Specification<Question> search(String kw) {
    return new Specification<>() {
      private static final long serialVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
        query.distinct(true);  // 중복을 제거
        Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
        Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
        Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
        return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
            cb.like(q.get("content"), "%" + kw + "%"),      // 내용
            cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
            cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
            cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
      }
    };
  }

  public Page<Question> getList(int page, String kw) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    Specification<Question> spec = search(kw);
    return this.questionRepository.findAll(spec, pageable);
//        return this.questionRepository.findAllByKeyword(kw, pageable);
  }

  public Question getQuestion(Integer id) {
    Optional<Question> question = this.questionRepository.findById(id);
    if (question.isPresent()) {
      return question.get();
    } else {
      throw new DataNotFoundException("question not found");
    }
  }

  public void create(QuestionForm questionForm, SiteUser user, MultipartFile[] files) throws Exception {

//    List<String> filename = new ArrayList<>();
//    List<String> filepath = new ArrayList<>();
//
//    for (MultipartFile file : files){
//      UUID uuid = UUID.randomUUID();
//      String fileName = uuid + "+" + file.getOriginalFilename();
//      String filePath = originPath + fileName;
//
//      File saveFile = new File(genFileDirPath, fileName);
//      file.transferTo(saveFile);
//
//      filename.add(fileName);
//      filepath.add(filePath);
//    }
    Question q = new Question();
    q.setSubject(questionForm.getSubject());
    q.setContent(questionForm.getContent());
    q.setCreateDate(LocalDateTime.now());
    q.setAuthor(user);

    this.questionRepository.save(q);
  }

  public void modify(Question question, String subject, String content) {
    question.setSubject(subject);
    question.setContent(content);
    question.setModifyDate(LocalDateTime.now());
    this.questionRepository.save(question);
  }

  public void delete(Question question) {
    this.questionRepository.delete(question);
  }

  public void vote(Question question, SiteUser siteUser) {
    question.getVoter().add(siteUser);
    this.questionRepository.save(question);
  }

  public void increaseViewCount(Question question){
    question.setViewCount(question.getViewCount()+1);
    this.questionRepository.save(question);
  }
}