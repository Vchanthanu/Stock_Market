package com.stockmarket.company.mongo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.mongo.model.CompanyDetails;

@Repository
public interface MongoCompanyRepository extends MongoRepository<CompanyDetails, String> {

	CompanyDetails findByCode(String code);

	List<String> findByCodeLike(String searchString);

}
