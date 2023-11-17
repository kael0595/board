package com.example.demo.genFile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GenFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String relTypeCode;

  private long relId;

  private String typeCode;

  private String type2Code;

  private String fileExtTypeCode;

  private String fileExtType2Code;

  private long fileSize;

  private long fileNo;

  private String fileExt;

  private String fileDir;

  private String originFileName;
}
