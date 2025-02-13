package com.sportyshoes.service;

import com.sportyshoes.model.Purchase;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<Purchase> getPurchasesReport(LocalDate startDate, LocalDate endDate, Long categoryId);
}
