package com.example.demo.genFile.service;

import com.example.demo.genFile.entity.GenFile;
import com.example.demo.genFile.repository.GenFileRepository;
import com.example.demo.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class GenFileService {

  private final GenFileRepository genFileRepository;


}
