package com.sportyshoes.controller;

import com.sportyshoes.model.Purchase;
import com.sportyshoes.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Example: GET /admin/reports/purchases?startDate=2025-01-01&endDate=2025-01-31&categoryId=1
    @GetMapping("/purchases")
    public List<Purchase> getPurchaseReports(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long categoryId) {
        return reportService.getPurchasesReport(startDate, endDate, categoryId);
    }
}
