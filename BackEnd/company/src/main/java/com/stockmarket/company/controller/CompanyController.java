package com.stockmarket.company.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.company.CompanyApplication;
import com.stockmarket.company.entity.Company;
import com.stockmarket.company.service.CompanyService;

@RestController
@RequestMapping("/api/v1.0/market/company")
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyApplication.class);
	@Autowired
	CompanyService companyService;

	@PostMapping("/register")
	public void registerCompany(@RequestBody Company company) {
		logger.info("Inside registerCompany method in CompanyController");
		companyService.registerCompany(company);
		logger.info("End of registerCompany method in CompanyController");

	}
}
