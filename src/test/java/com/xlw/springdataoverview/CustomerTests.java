package com.xlw.springdataoverview;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import com.xlw.springdataoverview.entity.Customer;
import com.xlw.springdataoverview.repository.CustomerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@DataJpaTest
public class CustomerTests {
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
	public void testFindCustomer() {
		final Customer customer = new Customer();
		customer.setName("Liangwu Xu");
		customer.setAge(55);
		customer.setGender("Male");
		
		entityManager.persist(customer);

		final Query query = entityManager.createNativeQuery("SELECT * FROM customer WHERE name = ?").setParameter(1, "Liangwu Xu");
		final List customers = query.getResultList(); 
		
		System.out.println("#### Number of Customers: " + customers.size());
		
	    List<Customer> customerList = customerRepository.findByName("Liangwu Xu");
	    System.out.println(customerList.get(0));

		assertThat(customerList)
			.hasSize(1)
			.first()
			.isEqualTo(customer);
		
	}

}
