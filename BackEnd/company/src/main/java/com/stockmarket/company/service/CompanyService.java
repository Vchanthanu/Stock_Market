package com.stockmarket.company.service;

import java.util.List;

import com.stockmarket.company.entity.Company;

public interface CompanyService {

	void registerCompany(Company company);

	List<Company> getAllCompanies();

	void deleteCompany(String companyCode);

}
