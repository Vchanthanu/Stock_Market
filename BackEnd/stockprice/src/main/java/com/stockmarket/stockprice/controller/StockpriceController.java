package com.stockmarket.stockprice.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.stockprice.StockpriceApplication;
import com.stockmarket.stockprice.entity.StockPrice;
import com.stockmarket.stockprice.exception.InvalidInputDataException;
import com.stockmarket.stockprice.service.StockpriceService;

@RestController
@RequestMapping("/api/v1.0/market/stock")
public class StockpriceController {
	@Autowired
	StockpriceService stockpriceService;
	private static final Logger logger = LoggerFactory.getLogger(StockpriceApplication.class);

	@PostMapping("/add")
	public void addStockPrice(@RequestBody @Valid List<StockPrice> stockPriceList) {
		logger.info("Inside addStockPrice method in StockpriceController");
		if (stockPriceList.isEmpty())
			throw new InvalidInputDataException("priceList cannot be empty");
		else
			stockpriceService.addStockPrice(stockPriceList);
		logger.info("End of addStockPrice method in StockpriceController");
	}

}
