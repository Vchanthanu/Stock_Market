package com.stockmarket.company.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.company.mongo.model.StockExchange;

@Repository
public interface MongoStockExchangeRepository extends MongoRepository<StockExchange, String> {

}
