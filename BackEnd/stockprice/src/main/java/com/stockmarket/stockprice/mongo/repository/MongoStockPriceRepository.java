package com.stockmarket.stockprice.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.stockprice.mongo.model.StockPriceDetails;

@Repository
public interface MongoStockPriceRepository extends MongoRepository<StockPriceDetails, String> {

	List<StockPriceDetails> findByCompanyCodeOrderByPriceUpdatedDateDesc(String string);

}
