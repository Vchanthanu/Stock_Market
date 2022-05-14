package com.stockmarket.company.mongo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.company.entity.StockExchange;
import com.stockmarket.company.mongo.model.CompanyDetails;

@Repository
public interface MongoCompanyRepository extends MongoRepository<CompanyDetails, String> {

	Optional<StockExchange> findByCode(String code);

}
