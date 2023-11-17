package com.example.demo.genFile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class GenFile {

  private String relTypeCode;

  private long relId;

  private String typeCode;

  private String fileExtTypeCode;

  private long fileSize;

  private long fileNo;

  private String fileExt;

  private String fileDir;

  private String originFileName;
}
