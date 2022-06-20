package com.stockmarket.kafkaconsumercomponent.service;

import java.util.List;

import com.stockmarket.kafkaconsumercomponent.model.CompanyDetails;
import com.stockmarket.kafkaconsumercomponent.model.StockPriceDetails;

public interface KafkaConsumerService {

	void addCompany(CompanyDetails companyDetails);

	void addStockPrice(List<StockPriceDetails> stockPrice);

	void deleteCompany(String code);

}
