package com.stockmarket.company.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.stockmarket.company.CompanyApplication;
import com.stockmarket.company.entity.Company;
import com.stockmarket.company.exception.ApplicationServiceException;
import com.stockmarket.company.exception.InvalidInputDataException;
import com.stockmarket.company.exception.NoDataFoundException;
import com.stockmarket.company.mongo.repository.MongoCompanyRepository;
import com.stockmarket.company.mongo.repository.MongoStockExchangeRepository;
import com.stockmarket.company.mongo.repository.MongoStockPriceRepository;
import com.stockmarket.company.repository.CompanyRepository;
import com.stockmarket.company.repository.StockExchangeRepository;
import com.stockmarket.company.service.CompanyService;
import com.stockmarket.mongo.model.CompanyDetails;
import com.stockmarket.mongo.model.StockExchange;
import com.stockmarket.mongo.model.StockPriceDetails;

@Service
public class CompanyServiceImpl implements CompanyService {
	private static final Logger logger = LoggerFactory.getLogger(CompanyApplication.class);
	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	StockExchangeRepository stockExchangeRepo;

	@Autowired
	MongoCompanyRepository mongoCompanyRepository;

	@Autowired
	MongoStockPriceRepository mongoStockPriceRepository;
	@Autowired
	MongoStockExchangeRepository mongoStockExchangeRepository;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void registerCompany(Company company) {
		logger.info("Inside registerCompany method in CompanyServiceImpl");
		try {
			if (mongoCompanyRepository.findByCode(company.getCode()) == null) {
				company.getStockPrice().forEach(stockPrice -> {
					if (stockPrice.getId() != null) {
						stockPrice.getId().setPriceUpdatedDate(new Date());
						stockPrice.setCompany(company);
						stockPrice.setStockExchange(
								stockExchangeRepo.findById(stockPrice.getId().getStockExchangeCode()).get());
					}
				});
				companyRepository.save(company);
				sendMessageToKafkaTopic(company);

			} else {
				throw new InvalidInputDataException("Company Code already exists!!");
			}
			logger.info("Inside registerCompany method in CompanyServiceImpl");
		} catch (InvalidInputDataException ex) {
			throw ex;
		}

		catch (Exception ex) {
			logger.error("Exception in registerCompany method-{}", ex.getMessage());
			throw new ApplicationServiceException(
					"Oops Something unexpected happened.Please try again later.Error: " + ex.getMessage());
		}
	}

	private void sendMessageToKafkaTopic(Company company) {
		CompanyDetails companyDetails = new CompanyDetails();
		companyDetails.setCeo(company.getCeo());
		companyDetails.setCode(company.getCode());
		companyDetails.setName(company.getName());
		companyDetails.setWebsite(company.getWebsite());
		companyDetails.setRequestType("ADDCOMPANY");
		companyDetails.setTurnover(company.getTurnover());
		List<StockPriceDetails> stockPriceList = new ArrayList<>();
		company.getStockPrice().stream().forEach(price -> {
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
		companyDetails.setStockPrice(stockPriceList);
		logger.info("message to topic" + companyDetails.toString());
		kafkaTemplate.send("stockmarket", companyDetails);
	}

	@Override
	public List<CompanyDetails> getAllCompanies() {
		try {
			logger.info("Inside getAllCompanies method in CompanyServiceImpl");
			List<CompanyDetails> companies = mongoCompanyRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
			if (!companies.isEmpty()) {
				List<StockExchange> stockExchangeList = mongoStockExchangeRepository.findAll();
				companies.stream().forEach(company -> {
					getCompanyStockPrice(stockExchangeList, company);
				});

				return companies;
			} else {
				throw new NoDataFoundException("No Companies available");
			}

		} catch (NoDataFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception in getAllCompanies method" + ex.getMessage());
			throw new ApplicationServiceException(
					"Oops Something unexpected happened.Please try again later.Error: " + ex.getMessage());
		}

	}

	private void getCompanyStockPrice(List<StockExchange> stockExchangeList, CompanyDetails company) {
		if (StringUtils.isNotBlank(company.getCode())) {
			List<StockPriceDetails> stockPriceList = mongoStockPriceRepository
					.findByCompanyCodeOrderByPriceUpdatedDateDesc((company.getCode()));
			if (!stockPriceList.isEmpty()) {
				List<StockPriceDetails> latestStockPriceList = new ArrayList<>();
				stockExchangeList.stream().forEach(stockExchange -> {
					StockPriceDetails latestStockPrice = stockPriceList.stream().filter(
							price -> price.getStockExchange().getCode().equalsIgnoreCase(stockExchange.getCode()))
							.findFirst().orElse(null);
					if (latestStockPrice != null) {
						latestStockPriceList.add(latestStockPrice);
					}
					company.setStockPrice(latestStockPriceList);

				});

			}

		}
	}

	@Override
	public void deleteCompany(String companyCode) {
		try {
			logger.info("Inside deleteCompany method in CompanyServiceImpl");
			companyRepository.deleteById(companyCode);
			CompanyDetails companyDetails = new CompanyDetails();
			companyDetails.setCode(companyCode);
			companyDetails.setRequestType("DELETE");
			logger.info("message to topic" + companyDetails.toString());
			kafkaTemplate.send("stockmarket", companyDetails);
			logger.info("End of deleteCompany method in CompanyServiceImpl");
		} catch (Exception ex) {
			logger.error("Exception in delete company method" + companyCode + ex.getMessage());
			throw new ApplicationServiceException(
					"Oops Something unexpected happened.Please try again later. Error: " + ex.getMessage());
		}
	}

	@Override
	public CompanyDetails getCompanyByCode(String companyCode) {

		try {
			logger.info("Inside getCompanyByCode method in CompanyServiceImpl");
			CompanyDetails company = mongoCompanyRepository.findByCode(companyCode);
			if (company != null) {
				List<StockExchange> stockExchangeList = mongoStockExchangeRepository.findAll();
				getCompanyStockPrice(stockExchangeList, company);
			} else {
				throw new NoDataFoundException("No data found for the company code" + companyCode);
			}
			logger.info("Company Details :: {}", company.toString());
			return company;
		} catch (NoDataFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception in getCompanyByCode method" + ex.getMessage());
			throw new ApplicationServiceException(
					"Oops Something unexpected happened.Please try again later.Error:" + ex.getMessage());
		}
	}

	@Override
	public List<CompanyDetails> getMatchingCompanies(String searchString) {
		try {
			logger.info("Inside getMatchingCompanies method in CompanyServiceImpl");
			Query query = new Query();
			query.addCriteria(Criteria.where("code").regex(searchString.trim(), "i"));
			List<CompanyDetails> companyDetails = mongoTemplate.find(query, CompanyDetails.class);
			if (companyDetails.size() > 0) {
				List<StockExchange> stockExchangeList = mongoStockExchangeRepository.findAll();
				companyDetails.stream().forEach(company -> {
					getCompanyStockPrice(stockExchangeList, company);
				});
				return companyDetails;

			} else {
				throw new NoDataFoundException("No Data available for the corresponding search");

			}

		} catch (NoDataFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception in getMatchingCompanies method." + ex.getMessage());
			throw new ApplicationServiceException(
					"Oops Something unexpected happened.Please try again later. Error: " + ex.getMessage());
		}
	}

}
