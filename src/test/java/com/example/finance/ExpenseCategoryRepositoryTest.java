package com.example.finance;

import com.example.finance.models.ExpenseCategory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ExpenseCategoryRepositoryTest {


    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    public void insertTest(){
        ExpenseCategory category = new ExpenseCategory("Rent");
        entityManager.persist(category);
        assertNotNull(category.getId());
    }
}
