package com.xlw.springdataoverview.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xlw.springdataoverview.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	public List<Customer> findByName(String name);
}
