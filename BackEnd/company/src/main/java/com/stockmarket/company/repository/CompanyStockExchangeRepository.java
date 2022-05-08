package com.stockmarket.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockmarket.company.entity.CompanyStockExchange;
import com.stockmarket.company.entity.StockPrice;

public interface CompanyStockExchangeRepository extends JpaRepository<StockPrice, CompanyStockExchange> {

}
