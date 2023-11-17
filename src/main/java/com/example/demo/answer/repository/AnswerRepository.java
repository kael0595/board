package com.example.demo.answer.repository;

import com.example.demo.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
  Optional<Answer> findById(Integer id);
}
