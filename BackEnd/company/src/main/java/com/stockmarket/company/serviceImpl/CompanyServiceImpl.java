package com.stockmarket.company.serviceImpl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockmarket.company.CompanyApplication;
import com.stockmarket.company.entity.Company;
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
	@Override
	public void registerCompany(Company company) {
		logger.info("Inside registerCompany method in CompanyServiceImpl");
		company.getStockPrice().forEach(stockPrice->
		{stockPrice.getId().setPriceUpdatedDate(new Date());
		stockPrice.setCompany(company);
		stockPrice.setStockExchange(stockExchangeRepo.findById(stockPrice.getId().getStockExchangeCode()).get());
		});
		companyRepository.save(company);

	}

	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	@Override
	public void deleteCompany(String companyCode) {
		companyRepository.deleteById(companyCode);
	}

}
