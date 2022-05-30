package com.stockmarket.company.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockmarket.company.CompanyApplication;
import com.stockmarket.company.entity.Company;
import com.stockmarket.company.exception.ApplicationServiceException;
import com.stockmarket.company.exception.InvalidInputDataException;
import com.stockmarket.company.exception.NoDataFoundException;
import com.stockmarket.company.mongo.model.CompanyDetails;
import com.stockmarket.company.mongo.model.StockExchange;
import com.stockmarket.company.mongo.model.StockPriceDetails;
import com.stockmarket.company.mongo.repository.MongoCompanyRepository;
import com.stockmarket.company.mongo.repository.MongoStockExchangeRepository;
import com.stockmarket.company.mongo.repository.MongoStockPriceRepository;
import com.stockmarket.company.repository.CompanyRepository;
import com.stockmarket.company.repository.StockExchangeRepository;
import com.stockmarket.company.service.CompanyService;

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

	@Override
	public void registerCompany(Company company) {
		logger.info("Inside registerCompany method in CompanyServiceImpl");
		try {
			if (mongoCompanyRepository.findByCode(company.getCode()).get() == null) {
				company.getStockPrice().forEach(stockPrice -> {
					if (stockPrice.getId() != null) {
						stockPrice.getId().setPriceUpdatedDate(new Date());
						stockPrice.setCompany(company);
						stockPrice.setStockExchange(
								stockExchangeRepo.findById(stockPrice.getId().getStockExchangeCode()).get());
					}
				});
				companyRepository.save(company);

			} else {
				throw new InvalidInputDataException("Company Code already exists!!");
			}
			logger.info("Inside registerCompany method in CompanyServiceImpl");
		} catch (InvalidInputDataException ex) {
			throw ex;
		}

		catch (Exception ex) {
			logger.error("Exception in registerCompany method-{}", ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later ");
		}
	}

	@Override
	public List<CompanyDetails> getAllCompanies() {
		try {
			logger.info("Inside getAllCompanies method in CompanyServiceImpl");
			List<CompanyDetails> companies = mongoCompanyRepository.findAll();
			if (!companies.isEmpty()) {
				List<StockExchange> stockExchangeList = mongoStockExchangeRepository.findAll();
				companies.stream().forEach(company -> {
					getCompanyStockPrice(stockExchangeList, company);
				});
				logger.info("End of  getAllCompanies method in CompanyServiceImpl");
				return companies;
			} else {
				throw new NoDataFoundException("No Companies available");
			}

		} catch (Exception ex) {
			logger.error("Exception in getAllCompanies method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later ");
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
			logger.info("End of deleteCompany method in CompanyServiceImpl");
		} catch (Exception ex) {
			logger.error("Exception in delete company method" + companyCode + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later ");
		}
	}

	@Override
	public CompanyDetails getCompanyByCode(String companyCode) {

		try {
			logger.info("Inside getCompanyByCode method in CompanyServiceImpl");
			CompanyDetails company = mongoCompanyRepository.findByCode(companyCode).get();
			if (company != null) {
				List<StockExchange> stockExchangeList = mongoStockExchangeRepository.findAll();
				getCompanyStockPrice(stockExchangeList, company);
			}
			logger.info("Company Details :: {}", company.toString());
			return company;
		} catch (Exception ex) {
			logger.error("Exception in getCompanyByCode method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later");
		}
	}

	@Override
	public List<String> getMatchingCompanyCodes(String searchString) {
		try {
			logger.info("Inside getMatchingCompanyCodes method in CompanyServiceImpl");
			List<String> searchList = mongoCompanyRepository.findByCodeLike(searchString);
			if (!searchList.isEmpty()) {
				return searchList;
			} else {
				throw new NoDataFoundException("No Data available for the corresponding search");
			}
		} catch (Exception ex) {
			logger.error("Exception in getMatchingCompanyCodes method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later");
		}
	}

}
