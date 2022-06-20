package com.stockmarket.kafkaconsumercomponent.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.kafkaconsumercomponent.model.StockPriceDetails;

@Repository
public interface MongoStockPriceRepository extends MongoRepository<StockPriceDetails, String> {

	List<StockPriceDetails> findByCompanyCodeOrderByPriceUpdatedDateDesc(String string);

	void deleteByCode(String code);

}
