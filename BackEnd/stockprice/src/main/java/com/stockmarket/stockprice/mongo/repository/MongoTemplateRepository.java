package com.stockmarket.stockprice.mongo.repository;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.stockmarket.stockprice.StockpriceApplication;
import com.stockmarket.stockprice.exception.ApplicationServiceException;
import com.stockmarket.stockprice.mongo.model.StockPriceDetails;

@Repository
public class MongoTemplateRepository {
	@Autowired
	MongoTemplate mongoTemplate;
	private static final Logger logger = LoggerFactory.getLogger(StockpriceApplication.class);

	public List<StockPriceDetails> getStockPriceBasedOnDateRange(String companyCode, String stockExchangeCode, Date fromDate,
			Date toDate) {
		try {
			logger.info("Inside getStockPriceBasedOnDateRange method in MongoTemplateRepository");
			Query query = new Query();
			query.addCriteria(new Criteria().andOperator(Criteria.where("companyCode").is(companyCode),
					Criteria.where("stockExchange.code").is(stockExchangeCode),
					Criteria.where("priceUpdatedDate").gte(fromDate), Criteria.where("priceUpdatedDate").lte(toDate)));
			return mongoTemplate.find(query, StockPriceDetails.class, "StockPrice");
		} catch (Exception ex) {
			logger.error("Exception in getStockPriceBasedOnDateRange method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later");
		}
	}

}
