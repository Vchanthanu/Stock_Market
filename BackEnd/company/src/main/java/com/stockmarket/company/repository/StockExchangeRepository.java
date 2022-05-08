package com.stockmarket.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockmarket.company.entity.StockExchange;

public interface StockExchangeRepository extends JpaRepository<StockExchange, String> {

}
