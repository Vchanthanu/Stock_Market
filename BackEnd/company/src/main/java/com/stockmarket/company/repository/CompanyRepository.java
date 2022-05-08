package com.stockmarket.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockmarket.company.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, String> {

	
}
