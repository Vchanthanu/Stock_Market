package com.stockmarket.stockprice.service;

import java.util.List;

import com.stockmarket.mongo.model.StockExchange;
import com.stockmarket.stockprice.dto.StockPriceDto;
import com.stockmarket.stockprice.entity.StockPrice;

public interface StockpriceService {

	void addStockPrice(List<StockPrice> priceList);

	List<StockExchange> getStockExchangeDetails();

	StockPriceDto getStockPriceBasedOnDateRange(String companyCode,
			String stockExchangeCode, String fromDate, String toDate);

}
