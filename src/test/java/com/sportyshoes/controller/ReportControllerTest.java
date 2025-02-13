package com.sportyshoes.controller;

import com.sportyshoes.model.Category;
import com.sportyshoes.model.Product;
import com.sportyshoes.model.Purchase;
import com.sportyshoes.model.User;
import com.sportyshoes.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    void testGetPurchaseReports() throws Exception {
        List<Purchase> purchase = new ArrayList<>();

        Category category = new Category("Running");
        category.setId(1L);
        Product product = new Product("Running Shoe A", 100.0, category);
        product.setId(1L);
        User user = new User("john_doe", "john@example.com", "test123");
        user.setId(1L);
        Purchase purchaseController = new Purchase(user, product, LocalDate.of(2025, 1, 10), 1);
        purchaseController.setId(1L);
        purchase.add(purchaseController);

        when(reportService.getPurchasesReport(any(LocalDate.class), any(LocalDate.class), any()))
                .thenReturn(purchase);

        mockMvc.perform(get("/admin/reports/purchases")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-31")
                        .param("categoryId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));
    }
}
