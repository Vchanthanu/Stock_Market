package com.stockmarket.company.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.company.mongo.model.StockPriceDetails;

@Repository
public interface MongoStockPriceRepository extends MongoRepository<StockPriceDetails, String> {

	List<StockPriceDetails> findByCompanyCodeOrderByPriceUpdatedDateDesc(String string);

}
