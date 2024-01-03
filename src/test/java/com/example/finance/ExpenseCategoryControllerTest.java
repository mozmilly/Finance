package com.example.finance;

import com.example.finance.controllers.ExpenseCategoryController;
import com.example.finance.models.ExpenseCategory;
import com.example.finance.repositories.ExpenseCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class ExpenseCategoryControllerTest {

    private MockMvc mvc;

    @Mock
    private ExpenseCategoryRepository expenseCategoryRepository;



    private JacksonTester<ExpenseCategory> jsonExpenseCategory;


    @InjectMocks
    private ExpenseCategoryController expenseCategoryController;

    @BeforeEach
    public void setup() {

        JacksonTester.initFields(this, new ObjectMapper());


        mvc = MockMvcBuilders.standaloneSetup(expenseCategoryController)
                .build();
    }

    @Test
    public void canCreateExpenseCategory() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/api/expense/category/add").contentType(MediaType.APPLICATION_JSON).content(
                        jsonExpenseCategory.write(new ExpenseCategory("Taxes")).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}