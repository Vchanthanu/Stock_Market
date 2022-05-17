package com.stockmarket.company.serviceImpl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockmarket.company.CompanyApplication;
import com.stockmarket.company.entity.Company;
import com.stockmarket.company.exception.ApplicationServiceException;
import com.stockmarket.company.exception.InvalidInputDataException;
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
		try {
			if (companyRepository.findById(company.getCode()).get() == null) {
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
		} catch (InvalidInputDataException ex) {
			throw ex;
		}

		catch (Exception ex) {
			logger.error("Exception in registerCompany method-{}", ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later ");
		}
	}

	@Override
	public List<Company> getAllCompanies() {
		try {
			logger.info("Inside getAllCompanies method in CompanyServiceImpl");
			return companyRepository.findAll();
		} catch (Exception ex) {
			logger.error("Exception in getAllCompanies method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later ");
		}
	}

	@Override
	public void deleteCompany(String companyCode) {
		try {
			logger.info("Inside deleteCompany method in CompanyServiceImpl");
			companyRepository.deleteById(companyCode);
		} catch (Exception ex) {
			logger.error("Exception in delete company method" + companyCode + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later ");
		}
	}

	@Override
	public Company getCompanyByCode(String companyCode) {

		try {
			logger.info("Inside getCompanyByCode method in CompanyServiceImpl");
			Company company = companyRepository.findByCode(companyCode);
			logger.info("Company Details :: {}", company.toString());
			return company;
		} catch (Exception ex) {
			logger.error("Exception in getCompanyByCode method" + ex.getMessage());
			throw new ApplicationServiceException("Oops Something unexpected happened.Please try again later");
		}
	}

}
