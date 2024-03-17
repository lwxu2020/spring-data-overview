package com.xlw.springdataoverview;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.xlw.springdataoverview.entity.Flight;
import com.xlw.springdataoverview.repository.FlightRepository;
import com.xlw.springdataoverview.service.FlightService;

//@DataJpaTest
@SpringBootTest
public class TransactionalTests {
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private FlightService flightService;
	
	@BeforeEach
	public void setUp() {
		flightRepository.deleteAll();
	}
	
	@Test
	public void shouldNotRollBackWhenTheresNoTransaction() {
		try {
			flightService.saveFlight(new Flight());
		}
		catch (Exception e) {
			// Do nothing
		}
		finally {
			Assertions.assertThat(flightRepository.findAll())
				.isNotEmpty();
		}
		
	}
	
	@Test
	public void shouldRollBackWhenTheresIsATransaction() {
		try {
			flightService.saveFlightTransactional(new Flight());
		}
		catch (Exception e) {
			// Do nothing
		}
		finally {
			Assertions.assertThat(flightRepository.findAll())
				.isEmpty();
		}
		
	}
}
