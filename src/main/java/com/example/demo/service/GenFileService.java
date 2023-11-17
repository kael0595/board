package com.example.demo.service;

import com.example.demo.repository.GenFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenFileService {

  private final GenFileRepository genFileRepository;
}
