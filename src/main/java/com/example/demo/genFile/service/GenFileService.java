package com.example.demo.genFile.service;

import com.example.demo.genFile.repository.GenFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenFileService {

  private final GenFileRepository genFileRepository;
}
