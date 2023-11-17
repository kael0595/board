package com.example.demo.genFile.repository;

import com.example.demo.genFile.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenFileRepository extends JpaRepository<GenFile, Long> {
}
