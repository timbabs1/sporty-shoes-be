package com.sportyshoes.service;

import com.sportyshoes.model.Category;
import com.sportyshoes.model.Purchase;
import com.sportyshoes.repository.CategoryRepository;
import com.sportyshoes.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testGetPurchasesReportByDate() {
        // DataLoader creates some purchase records in January and February 2025.
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 31);
        List<Purchase> purchase = reportService.getPurchasesReport(startDate, endDate, null);
        assertNotNull(purchase);
        // Expecting at least two purchases from January.
        assertTrue(purchase.size() >= 2);
    }

    @Test
    void testGetPurchasesReportByDateAndCategory() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 2, 28);
        List<Category> categories = categoryRepository.findAll();
        assertFalse(categories.isEmpty(), "At least one category should exist");
        Category category = categories.get(0);
        List<Purchase> purchase = reportService.getPurchasesReport(startDate, endDate, category.getId());
        assertNotNull(purchase);
        for (Purchase p : purchase) {
            assertEquals(category.getId(), p.getProduct().getCategory().getId());
        }
    }
}
