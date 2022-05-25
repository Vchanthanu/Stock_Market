package com.stockmarket.stockprice.service;

import java.util.List;


import com.stockmarket.stockprice.entity.StockPrice;
import com.stockmarket.stockprice.mongo.model.StockExchange;

public interface StockpriceService {

	void addStockPrice(List<StockPrice> priceList);
	List<StockExchange> getStockExchangeDetails();

}
