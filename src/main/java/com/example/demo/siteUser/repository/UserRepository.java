package com.example.demo.siteUser.repository;

import com.example.demo.siteUser.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
  Optional<SiteUser> findByUsername(String username);

  Optional<SiteUser> findByEmail(String email);

  Optional<SiteUser> findByEmailAndUsername(String email, String username);

  SiteUser findUserByEmail(String userEmail);
}
