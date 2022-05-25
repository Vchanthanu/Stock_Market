package com.stockmarket.stockprice.mongo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.stockprice.mongo.model.CompanyDetails;

@Repository
public interface MongoCompanyRepository extends MongoRepository<CompanyDetails, String> {

	Optional<CompanyDetails> findByCode(String code);

}
