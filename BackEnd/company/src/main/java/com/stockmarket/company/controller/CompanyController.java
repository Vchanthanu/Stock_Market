package com.stockmarket.company.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.company.CompanyApplication;
import com.stockmarket.company.entity.Company;
import com.stockmarket.company.service.CompanyService;
import com.stockmarket.mongo.model.CompanyDetails;

@RestController
@RequestMapping("/api/v1.0/market/company")
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyApplication.class);
	@Autowired
	CompanyService companyService;

	@GetMapping("/all")
	public List<CompanyDetails> getAllCompanies() {
		logger.info("Inside getAllCompanies method in CompanyController");
		return companyService.getAllCompanies();
	}

	@PostMapping("/register")
	public void registerCompany(@RequestBody @Valid Company company) {
		logger.info("Inside registerCompany method in CompanyController");
		companyService.registerCompany(company);
		logger.info("End of registerCompany method in CompanyController");
	}

	@DeleteMapping("/delete/{companyCode}")
	public void deleteCompany(@PathVariable String companyCode) {
		logger.info("Inside deleteCompany method in CompanyController :companyCode{}" + companyCode);
		companyService.deleteCompany(companyCode);
		logger.info("End of deleteCompany method in CompanyController");
	}

	@GetMapping("/get/{companyCode}")
	public CompanyDetails getCompanyByCode(@PathVariable String companyCode) {
		logger.info("Request received for getCompanyByCode, :: code:{}",companyCode);
		return companyService.getCompanyByCode(companyCode);
	}
	
	@GetMapping("/search/{searchString}")
	public List<String> getMatchingCompanyCodes(@PathVariable String searchString) {
		logger.info("Request received for getMatchingCompanyCodes, :: searchString:{}",searchString);
		return companyService.getMatchingCompanyCodes(searchString);
	}

}
