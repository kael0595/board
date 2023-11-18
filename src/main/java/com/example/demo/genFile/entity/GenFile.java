package com.example.demo.genFile.entity;

import com.example.demo.question.entity.Question;
import com.example.demo.review.entity.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

  private String filepath;

  private String filename;

  @ElementCollection
  @CollectionTable(name = "gen_file_filenames", joinColumns = @JoinColumn(name = "gen_file_id"))
  @Column(name = "filename")
  private List<String> filenames;

  @ElementCollection
  @CollectionTable(name = "gen_file_file_paths", joinColumns = @JoinColumn(name = "gen_file_id"))
  @Column(name = "file_path")
  private List<String> filePaths;
}
