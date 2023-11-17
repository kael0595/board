package com.example.demo.genFile.repository;

import com.example.demo.genFile.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenFileRepository extends JpaRepository<GenFile, Long> {
  Optional<Object> findTop1ByRelTypeCodeAndRelIdAndTypeCodeAndType2CodeOrderByFileNoDesc(String relTypeCode, Long relId, String typeCode, String type2Code);
}
