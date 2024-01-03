package com.example.finance.repositories;

import com.example.finance.models.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {



}
