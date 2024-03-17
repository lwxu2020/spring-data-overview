package com.xlw.springdataoverview;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.xlw.springdataoverview.entity.Flight;
import com.xlw.springdataoverview.repository.FlightRepository;

@DataJpaTest
public class CrudTests {

	@Autowired
	private FlightRepository flightRepository;
	
	@Test
	public void shouldPerformCRUDOperations() {
		final Flight flight = new Flight();
		flight.setOrigin("London");
		flight.setDestination("New York");
		flight.setScheduledAt(LocalDateTime.parse("2011-12-13T12:12:00"));
		
		flightRepository.save(flight);
		final Iterable<Flight> flights = flightRepository.findAll();
		System.out.println("#### Flight:");
		System.out.println(flights.iterator().next());
		assertThat(flights)
			.hasSize(1)
			.first()
			.isEqualToComparingFieldByField(flight);
	    flightRepository.deleteById(flight.getId());	
	    
	    assertThat(flightRepository.count()).isZero();
	
	    
	}
}
