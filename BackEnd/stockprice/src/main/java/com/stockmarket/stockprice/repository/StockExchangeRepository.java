package com.stockmarket.stockprice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.stockprice.entity.StockExchange;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange, String> {

}
