package com.example.finance.services;

import com.example.finance.models.AuditTrail;
import com.example.finance.models.User;
import com.example.finance.repositories.AuditTrailRepository;
import com.example.finance.repositories.UserRepository;
import com.example.finance.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class ServiceClass {
    @Autowired
    private AuditTrailRepository auditTrailRepository;

    @Autowired
    private UserRepository userRepository;
    public void auditTrail(Authentication authentication, String desc, String name){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getId());
        auditTrailRepository.save(new AuditTrail(user, desc+" "+name));
    }
}
