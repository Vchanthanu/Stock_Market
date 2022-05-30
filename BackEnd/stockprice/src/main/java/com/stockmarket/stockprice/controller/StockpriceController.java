package com.stockmarket.stockprice.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.stockprice.StockpriceApplication;
import com.stockmarket.stockprice.dto.StockPriceDto;
import com.stockmarket.stockprice.entity.StockPrice;
import com.stockmarket.stockprice.exception.InvalidInputDataException;
import com.stockmarket.stockprice.mongo.model.StockExchange;
import com.stockmarket.stockprice.service.StockpriceService;
import com.stockmarket.stockprice.util.ValidationUtil;

@RestController
@RequestMapping("/api/v1.0/market/stock")
public class StockpriceController {
	@Autowired
	StockpriceService stockpriceService;
	@Autowired
	ValidationUtil validationUtil;
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

	@GetMapping("/get/stockExchange")
	public List<StockExchange> getStockExchangeDetails() {
		logger.info("Request received for getStockExchangeDetails");
		try {
			return stockpriceService.getStockExchangeDetails();
		} catch (Exception e) {
			logger.error("Exception occured on Request {} ", e);
			throw e;
		}

	}

	@GetMapping("/get/{companyCode}")
	public StockPriceDto getStockPriceBasedOnDateRange(@PathVariable String companyCode,
			@RequestParam String stockExchangeCode, @RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Request received for getStockPriceBasedOnDateRange :: code:{}", companyCode, "fromDate:{}",
				fromDate, "toDate:{}", toDate);
		try {
			validationUtil.validateGetStockPriceRequest(companyCode, stockExchangeCode, fromDate, toDate);
			return stockpriceService.getStockPriceBasedOnDateRange(companyCode, stockExchangeCode, fromDate, toDate);
		} catch (Exception e) {
			logger.error("Exception occured on Request {} ", e);
			throw e;
		}

	}

}
