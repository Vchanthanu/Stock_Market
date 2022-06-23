package com.stockmarket.kafkaconsumercomponent.service;

import java.util.List;

import com.stockmarket.mongo.model.CompanyDetails;
import com.stockmarket.mongo.model.StockPriceDetails;

public interface KafkaConsumerService {

	void addCompany(CompanyDetails companyDetails);

	void addStockPrice(List<StockPriceDetails> stockPrice);

	void deleteCompany(String code);

}
