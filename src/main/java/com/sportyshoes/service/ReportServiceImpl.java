package com.sportyshoes.service;

import com.sportyshoes.model.Purchase;
import com.sportyshoes.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public List<Purchase> getPurchasesReport(LocalDate startDate, LocalDate endDate, Long categoryId) {
        if (categoryId != null) {
            return purchaseRepository.findByPurchaseDateBetweenAndProduct_Category_Id(startDate, endDate, categoryId);
        }
        return purchaseRepository.findByPurchaseDateBetween(startDate, endDate);
    }
}
