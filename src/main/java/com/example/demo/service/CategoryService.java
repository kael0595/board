package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public void create(String name) {
    Category category = new Category();
    category.setName(name);
    this.categoryRepository.save(category);
  }

  public List<Category> getList() {
    return this.categoryRepository.findAll();
  }
}
