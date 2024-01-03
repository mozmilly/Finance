package com.example.finance.controllers;

import com.example.finance.models.Expense;
import com.example.finance.models.ExpenseCategory;
import com.example.finance.models.User;
import com.example.finance.payload.request.ExpenseCategoryRequest;
import com.example.finance.payload.request.ExpenseRequest;
import com.example.finance.repositories.ExpenseCategoryRepository;
import com.example.finance.repositories.ExpenseRepository;
import com.example.finance.repositories.UserRepository;
import com.example.finance.security.services.UserDetailsImpl;
import com.example.finance.services.ServiceClass;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/expense/")
public class ExpenseController {
    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceClass service;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addExpenseCategory(@Valid @RequestBody ExpenseRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Expense expense = new Expense();
        expense.setName(request.getName());
        expense.setAmount(request.getAmount());
        ExpenseCategory category = expenseCategoryRepository.getReferenceById(request.getCategory());
        expense.setExpenseCategory(category);
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getId());
        expense.setUser(user);

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getDueDate());
            expense.setDueDate(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        expenseRepository.save(expense);
        service.auditTrail(auth,  "Added expense:>>", expense.getName());
        return ResponseEntity.ok(expense);
    }
}
