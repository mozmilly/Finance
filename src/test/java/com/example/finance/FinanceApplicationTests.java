package com.example.finance;

import com.example.finance.payload.request.LoginRequest;
import com.example.finance.payload.request.SignupRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FinanceApplicationTests {

    private Validator validator;

    @Test
    void contextLoads() {
    }

    @Test
    public void requiredUsernamePassword(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        LoginRequest request =  new LoginRequest("Helo", "Moses");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void usernamePasswordLengths(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        SignupRequest request =  new SignupRequest("mozde", "mozde@gmail.com", roles, "1234");
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}
