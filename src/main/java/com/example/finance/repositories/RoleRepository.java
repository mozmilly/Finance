package com.example.finance.repositories;

import java.util.Optional;

import com.example.finance.models.ERole;
import com.example.finance.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}