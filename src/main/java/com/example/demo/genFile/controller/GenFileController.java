package com.example.demo.genFile.controller;

import com.example.demo.genFile.service.GenFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class GenFileController {

  private final GenFileService genFileService;

  @PostMapping("/upload")
  @ResponseBody
  public String upload(MultipartFile file) throws Exception{
    this.genFileService.upload(file);
    return "true";
  }


}
