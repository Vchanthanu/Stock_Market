package com.stockmarket.stockprice.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.stockmarket.stockprice.StockpriceApplication;
import com.stockmarket.stockprice.dto.StockPriceDto;
import com.stockmarket.stockprice.entity.StockPrice;
import com.stockmarket.stockprice.exception.ApplicationServiceException;
import com.stockmarket.stockprice.exception.NoDataFoundException;
import com.stockmarket.stockprice.mongo.model.CompanyDetails;
import com.stockmarket.stockprice.mongo.model.StockExchange;
import com.stockmarket.stockprice.mongo.model.StockPriceDetails;
import com.stockmarket.stockprice.mongo.repository.MongoStockExchangeRepository;
import com.stockmarket.stockprice.mongo.repository.MongoStockPriceRepository;
import com.stockmarket.stockprice.mongo.repository.MongoTemplateRepository;
import com.stockmarket.stockprice.repository.CompanyRepository;
import com.stockmarket.stockprice.repository.CompanyStockExchangeRepository;
import com.stockmarket.stockprice.repository.StockExchangeRepository;
import com.stockmarket.stockprice.service.StockpriceService;
import com.stockmarket.stockprice.util.DateUtil;

@Service
public class StockpriceServiceImpl implements StockpriceService {
	private static final Logger logger = LoggerFactory.getLogger(StockpriceApplication.class);
	@Autowired
	CompanyStockExchangeRepository companyStockExchangeRepository;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	StockExchangeRepository stockExchangeRepository;

	@Autowired
	MongoStockExchangeRepository mongoStockExchangeRepository;

	@Autowired
	MongoStockPriceRepository mongoStockPriceRepository;

	@Autowired
	MongoTemplateRepository mongoTemplateRepository;

	@Autowired
	DateUtil dateUtil;
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void addStockPrice(List<StockPrice> priceList) {
		try {
			logger.info("Inside addStockPrice method in StockpriceServiceImpl");
			List<StockPriceDetails> stockPriceList = new ArrayList<>();
			priceList.stream().forEach(price -> {
				price.getId().setPriceUpdatedDate(new Date());
				price.setCompany(companyRepository.findById(price.getId().getCompanyCode()).get());
				price.setStockExchange(stockExchangeRepository.findById(price.getId().getStockExchangeCode()).get());
				StockPriceDetails stockPriceDetails = new StockPriceDetails();
				stockPriceDetails.setCompanyCode(price.getCompany().getCode());
				stockPriceDetails.setPriceUpdatedDate(price.getId().getPriceUpdatedDate());
				stockPriceDetails.setStockPrice(price.getStockPrice());
				StockExchange stockExchange = new StockExchange();
				stockExchange.setCode(price.getStockExchange().getCode());
				stockExchange.setName(price.getStockExchange().getName());
				stockPriceDetails.setStockExchange(stockExchange);
				stockPriceList.add(stockPriceDetails);
			});
			companyStockExchangeRepository.saveAll(priceList);
			sendMessageToKafkaTopic(stockPriceList);
			logger.info("End of  addStockPrice method in StockpriceServiceImpl");
		} catch (Exception ex) {
			logger.error("Exception in addStockPrice method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later");
		}

	}

	private void sendMessageToKafkaTopic(List<StockPriceDetails> stockPriceList) {
		CompanyDetails companyDetails = new CompanyDetails();
		companyDetails.setStockPrice(stockPriceList);
		companyDetails.setRequestType("ADDSTOCKPRICE");
		logger.info("message to topic" + companyDetails.toString());
		kafkaTemplate.send("stockmarket", companyDetails);
	}

	@Override
	public List<StockExchange> getStockExchangeDetails() {
		try {
			logger.info("Inside getStockExchangeDetails method in StockpriceServiceImpl");
			List<StockExchange> stockExchangeList = mongoStockExchangeRepository.findAll();
			logger.info("Stock Exchange Details :: {}", stockExchangeList.toString());
			return stockExchangeList;
		} catch (Exception ex) {
			logger.error("Exception in getStockExchangeDetails method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later");
		}

	}

	@Override
	public StockPriceDto getStockPriceBasedOnDateRange(String companyCode, String stockExchangeCode, String fromDate,
			String toDate) {
		try {
			logger.info("Inside getStockPriceBasedOnDateRange method in StockpriceServiceImpl");
			List<com.stockmarket.stockprice.mongo.model.StockPriceDetails> stockPriceList = mongoTemplateRepository
					.getStockPriceBasedOnDateRange(companyCode, stockExchangeCode, dateUtil.getDate(fromDate),
							dateUtil.getDate(toDate));
			if (!stockPriceList.isEmpty()) {
				List<Float> priceList = stockPriceList.stream().map(price -> price.getStockPrice())
						.collect(Collectors.toList());
				if (!priceList.isEmpty()) {
					Collections.sort(priceList);
					StockPriceDto stockPriceDto = new StockPriceDto();
					stockPriceDto.setMin(priceList.get(0));
					stockPriceDto.setMax(priceList.get(priceList.size() - 1));
					stockPriceDto
							.setAverage((float) priceList.stream().mapToDouble(price -> price).average().orElse(0.0));
					stockPriceDto.setPriceList(priceList);
					return stockPriceDto;

				}
			} else {
				throw new NoDataFoundException(
						"No Data available for the selected company for the selected date range");
			}
		} catch (Exception ex) {
			logger.error("Exception in getStockExchangeDetails method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later");
		}
		return null;
	}

}
