package com.stockmarket.stockprice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockmarket.stockprice.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, String> {

	Company findByCode(String code);

}
