package com.example.finance.repositories;

import java.util.Optional;

import com.example.finance.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.username = ?2")
  @Modifying
  public void updateFailedAttempts(int failAttempts, String email);
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}