package com.sportyshoes.repository;

import com.sportyshoes.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

    // To filter by date and category (joining product and category)
    List<Purchase> findByPurchaseDateBetweenAndProduct_Category_Id(LocalDate startDate, LocalDate endDate, Long categoryId);
}
