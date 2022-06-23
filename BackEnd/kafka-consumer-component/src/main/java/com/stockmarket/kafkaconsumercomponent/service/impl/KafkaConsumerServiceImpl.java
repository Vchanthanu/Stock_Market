package com.stockmarket.kafkaconsumercomponent.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockmarket.kafkaconsumercomponent.KafkaConsumerComponentApplication;
import com.stockmarket.kafkaconsumercomponent.repository.MongoCompanyRepository;
import com.stockmarket.kafkaconsumercomponent.repository.MongoStockPriceRepository;
import com.stockmarket.kafkaconsumercomponent.service.KafkaConsumerService;
import com.stockmarket.mongo.model.CompanyDetails;
import com.stockmarket.mongo.model.StockPriceDetails;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
	@Autowired
	MongoCompanyRepository mongoCompanyRepository;
	@Autowired
	MongoStockPriceRepository mongoStockPriceRepository;
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerComponentApplication.class);

	@Override
	public void addCompany(CompanyDetails companyDetails) {
		try {
			logger.info("Inside addCompany method in KafkaConsumerServiceImpl:{}" + companyDetails);
			mongoStockPriceRepository.saveAll(companyDetails.getStockPrice());
			companyDetails.setStockPrice(null);
			mongoCompanyRepository.save(companyDetails);
		} catch (Exception ex) {
			logger.info("Exception occured while adding company to mongo db:{}" + companyDetails);
		}
	}

	@Override
	public void addStockPrice(List<StockPriceDetails> stockPrice) {
		try {
			logger.info("Inside addStockPrice method in KafkaConsumerServiceImpl:{}");
			mongoStockPriceRepository.saveAll(stockPrice);
		} catch (Exception ex) {
			logger.info("Exception occured while adding stockprice to mongo db:{}" + stockPrice);
		}
	}

	@Override
	public void deleteCompany(String code) {
		try {
			logger.info("Inside deleteCompany method in KafkaConsumerServiceImpl:{}" + code);
			mongoCompanyRepository.deleteByCode(code);
			mongoStockPriceRepository.deleteByCompanyCode(code);
		} catch (Exception ex) {
			logger.info("Exception occured while deleting company to mongo db:{}" + code);
		}
	}

}
