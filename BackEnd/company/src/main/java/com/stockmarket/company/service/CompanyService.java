package com.stockmarket.company.service;

import java.util.List;

import com.stockmarket.company.entity.Company;
import com.stockmarket.company.mongo.model.CompanyDetails;

public interface CompanyService {

	void registerCompany(Company company);

	List<CompanyDetails> getAllCompanies();

	void deleteCompany(String companyCode);
	
	Company getCompanyByCode(String companyCode);

}
