package com.example.demo.genFile.controller;

import com.example.demo.genFile.service.GenFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class GenFileController {

  private final GenFileService genFileService;


}
