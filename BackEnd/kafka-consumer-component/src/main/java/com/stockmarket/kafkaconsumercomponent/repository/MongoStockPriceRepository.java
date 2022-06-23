package com.stockmarket.kafkaconsumercomponent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.mongo.model.StockPriceDetails;

@Repository
public interface MongoStockPriceRepository extends MongoRepository<StockPriceDetails, String> {

	void deleteByCompanyCode(String code);

}
