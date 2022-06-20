package com.stockmarket.stockprice.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.stockprice.mongo.model.StockExchange;

@Repository
public interface MongoStockExchangeRepository extends MongoRepository<StockExchange, String> {

}
