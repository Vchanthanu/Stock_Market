package com.stockmarket.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockmarket.authentication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	/*
	 * 
	 * */
	User findByEmail(String email);
}
