package com.stockmarket.kafkaconsumercomponent.message;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.stockmarket.kafkaconsumercomponent.KafkaConsumerComponentApplication;
import com.stockmarket.kafkaconsumercomponent.service.KafkaConsumerService;
import com.stockmarket.mongo.model.CompanyDetails;

@Component
public class KafkaMessageConsumer {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerComponentApplication.class);
	@Autowired
	KafkaConsumerService kafkaConsumerService;

	@KafkaListener(topics = "stockmarket", groupId = "group_id")
	public void consume(CompanyDetails companyDetails) {
		logger.info(String.format("Consumed message from topic -> %s", companyDetails));
		if (companyDetails != null) {
			if (companyDetails.getRequestType().equalsIgnoreCase("ADDCOMPANY")) {
				kafkaConsumerService.addCompany(companyDetails);
			} else if (companyDetails.getRequestType().equalsIgnoreCase("ADDSTOCKPRICE")
					&& !companyDetails.getStockPrice().isEmpty()) {
				kafkaConsumerService.addStockPrice(companyDetails.getStockPrice());
			} else if (companyDetails.getRequestType().equalsIgnoreCase("DELETE")
					&& StringUtils.isNotBlank(companyDetails.getCode())) {
				kafkaConsumerService.deleteCompany(companyDetails.getCode());
			}
		}
	}
}
