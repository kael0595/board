package com.example.demo.repository;

import com.example.demo.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenFileRepository extends JpaRepository<GenFile, Long> {
}
