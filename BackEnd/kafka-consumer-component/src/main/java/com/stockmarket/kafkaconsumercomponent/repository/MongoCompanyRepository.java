package com.stockmarket.kafkaconsumercomponent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.kafkaconsumercomponent.model.CompanyDetails;

@Repository
public interface MongoCompanyRepository extends MongoRepository<CompanyDetails, String> {

	void deleteByCode(String code);

}
