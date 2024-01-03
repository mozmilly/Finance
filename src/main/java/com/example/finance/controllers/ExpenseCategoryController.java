package com.example.finance.controllers;

import com.example.finance.models.AuditTrail;
import com.example.finance.models.ExpenseCategory;
import com.example.finance.models.User;
import com.example.finance.payload.request.ExpenseCategoryRequest;
import com.example.finance.repositories.AuditTrailRepository;
import com.example.finance.repositories.ExpenseCategoryRepository;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/expense/category")
public class ExpenseCategoryController {
    @Autowired
    ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    ServiceClass service;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addExpenseCategory(@Valid @RequestBody ExpenseCategoryRequest request){
        ExpenseCategory category = new ExpenseCategory(request.getName());
        expenseCategoryRepository.save(category);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        service.auditTrail(auth,  "Added expense category:", category.getName());
        return ResponseEntity.ok(category);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getExpenseCategory(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        service.auditTrail(auth, "Get a list of all expense categories", "");

        return ResponseEntity.ok(expenseCategoryRepository.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getOneExpenseCategory(@PathVariable long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        service.auditTrail(auth, "Got expense category with id ", String.valueOf(id));

        return ResponseEntity.ok(expenseCategoryRepository.findById(id));
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteExpenseCategory(@PathVariable long id){
        expenseCategoryRepository.deleteById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        service.auditTrail(auth, "Deleted expense category with id ", String.valueOf(id));

        return ResponseEntity.ok("Category deleted successfully");
    }


}
