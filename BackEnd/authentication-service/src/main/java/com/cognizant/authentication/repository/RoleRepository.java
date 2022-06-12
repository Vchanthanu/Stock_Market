package com.cognizant.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.authentication.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
