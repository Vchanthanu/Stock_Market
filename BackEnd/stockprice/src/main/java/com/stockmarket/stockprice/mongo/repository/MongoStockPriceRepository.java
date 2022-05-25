package com.stockmarket.stockprice.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.stockprice.mongo.model.StockPrice;

@Repository
public interface MongoStockPriceRepository extends MongoRepository<StockPrice, String> {

	List<StockPrice> findByCompanyCodeOrderByPriceUpdatedDateDesc(String string);

}
