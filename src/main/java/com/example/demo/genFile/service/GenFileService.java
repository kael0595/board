package com.example.demo.genFile.service;

import com.example.demo.genFile.entity.GenFile;
import com.example.demo.genFile.repository.GenFileRepository;
import com.example.demo.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenFileService {

  @Value("${custom.genFileDirPath}")
  private String genFileDirPath;
  @Value("${custom.originPath}")
  private String originPath;

  private final GenFileRepository genFileRepository;

  public GenFile upload(MultipartFile file) throws Exception{
    String projectPath = genFileDirPath;
    String originPaths = originPath;

    UUID uuid = UUID.randomUUID();
    String fileName = uuid + "_" + file.getOriginalFilename();
    String filePath = originPath + fileName;

    File saveFile = new File(projectPath, fileName);
    file.transferTo(saveFile);

    GenFile genFile = GenFile.builder()
        .filename(fileName)
        .filepath(filePath)
        .build();

    return this.genFileRepository.save(genFile);
  }


}
