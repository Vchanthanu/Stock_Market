package com.stockmarket.stockprice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.stockprice.entity.CompanyStockExchangePk;
import com.stockmarket.stockprice.entity.StockPrice;
@Repository
public interface CompanyStockExchangeRepository extends JpaRepository<StockPrice, CompanyStockExchangePk> {

}
