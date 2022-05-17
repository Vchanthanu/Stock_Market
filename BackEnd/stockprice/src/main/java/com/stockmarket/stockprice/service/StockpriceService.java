package com.stockmarket.stockprice.service;

import java.util.List;

import com.stockmarket.stockprice.entity.StockExchange;
import com.stockmarket.stockprice.entity.StockPrice;

public interface StockpriceService {

	void addStockPrice(List<StockPrice> priceList);
	List<StockExchange> getStockExchangeDetails();

}
