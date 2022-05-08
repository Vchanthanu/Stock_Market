package com.stockmarket.company.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockmarket.company.CompanyApplication;
import com.stockmarket.company.entity.Company;
import com.stockmarket.company.repository.CompanyRepository;
import com.stockmarket.company.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	private static final Logger logger = LoggerFactory.getLogger(CompanyApplication.class);
	@Autowired
	CompanyRepository companyRepository;

	@Override
	public void registerCompany(Company company) {
		logger.info("Inside registerCompany method in CompanyServiceImpl");
		companyRepository.save(company);

	}
}
