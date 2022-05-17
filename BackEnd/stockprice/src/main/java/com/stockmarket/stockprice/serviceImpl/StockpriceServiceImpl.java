package com.stockmarket.stockprice.serviceImpl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockmarket.stockprice.StockpriceApplication;
import com.stockmarket.stockprice.entity.StockExchange;
import com.stockmarket.stockprice.entity.StockPrice;
import com.stockmarket.stockprice.exception.ApplicationServiceException;
import com.stockmarket.stockprice.repository.CompanyRepository;
import com.stockmarket.stockprice.repository.CompanyStockExchangeRepository;
import com.stockmarket.stockprice.repository.StockExchangeRepository;
import com.stockmarket.stockprice.service.StockpriceService;

@Service
public class StockpriceServiceImpl implements StockpriceService {
	private static final Logger logger = LoggerFactory.getLogger(StockpriceApplication.class);
	@Autowired
	CompanyStockExchangeRepository companyStockExchangeRepository;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	StockExchangeRepository stockExchangeRepository;

	@Override
	public void addStockPrice(List<StockPrice> priceList) {
		try {
			logger.info("Inside addStockPrice method in StockpriceServiceImpl");
			priceList.stream().forEach(price -> {
				price.getId().setPriceUpdatedDate(new Date());
				price.setCompany(companyRepository.findById(price.getId().getCompanyCode()).get());
				price.setStockExchange(stockExchangeRepository.findById(price.getId().getStockExchangeCode()).get());
			});
			companyStockExchangeRepository.saveAll(priceList);
			logger.info("End of  addStockPrice method in StockpriceServiceImpl");
		} catch (Exception ex) {
			logger.error("Exception in addStockPrice method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later");
		}

	}

	@Override
	public List<StockExchange> getStockExchangeDetails() {
		try {
			logger.info("Inside getStockExchangeDetails method in StockpriceServiceImpl");
			List<StockExchange> stockExchangeList= stockExchangeRepository.findAll();
			logger.info("Stock Exchange Details :: {}",stockExchangeList.toString());
			return stockExchangeList;
		} catch (Exception ex) {
			logger.error("Exception in getStockExchangeDetails method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later");
		}
		
	}

}
