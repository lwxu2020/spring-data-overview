package com.xlw.springdataoverview;

import java.time.LocalDateTime;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.xlw.springdataoverview.entity.Flight;
import com.xlw.springdataoverview.repository.FlightRepository;

@DataJpaTest
public class PagingAndSortingTests {
	
	@Autowired
	private FlightRepository flightRepository;
	
	@BeforeEach
	public void setUp() {
		flightRepository.deleteAll();
	}
	
	@Test
	public void shouldSortFlightsByDestination() {
		final Flight flight1 = createFlight("Madrid");
		final Flight flight2 = createFlight("London");
		final Flight flight3 = createFlight("Paris");
		
		flightRepository.save(flight1);
		flightRepository.save(flight2);
		flightRepository.save(flight3);
		
		final Iterable<Flight> flights = flightRepository.findAll(Sort.by("destination"));
		assertThat(flights).hasSize(3);
		
		final Iterator<Flight> iterator = flights.iterator();
		
		assertThat(iterator.next().getDestination()).isEqualTo("London");
		assertThat(iterator.next().getDestination()).isEqualTo("Madrid");
		assertThat(iterator.next().getDestination()).isEqualTo("Paris");
		
	}
	
	@Test
	public void shouldSortFlightsByScheduledAndThenName() {
		final LocalDateTime now = LocalDateTime.now();

		final Flight paris1 = createFlight("Paris", now);
		final Flight paris2 = createFlight("Paris", now.plusHours(2));
		final Flight paris3 = createFlight("Paris", now.plusHours(1));
		final Flight london1 = createFlight("London", now.plusHours(1));
		final Flight london2 = createFlight("London", now);

		flightRepository.save(paris1);
		flightRepository.save(paris2);
		flightRepository.save(paris3);
		flightRepository.save(london1);
		flightRepository.save(london2);
		
		final Iterable<Flight> flights = flightRepository.findAll(Sort.by("destination", "scheduledAt"));
	    assertThat(flights).hasSize(5);
	    
	    final Iterator<Flight> iterator = flights.iterator();
	    assertThat(iterator.next()).isEqualToComparingFieldByField(london2);
	    assertThat(iterator.next()).isEqualToComparingFieldByField(london1);
	    assertThat(iterator.next()).isEqualToComparingFieldByField(paris1);
	    assertThat(iterator.next()).isEqualToComparingFieldByField(paris3);
	    assertThat(iterator.next()).isEqualToComparingFieldByField(paris2);
	}
	
	
	@Test
	public void shouldPageResults() {
		for (int i=0; i<50; i++) {
			flightRepository.save(createFlight(String.valueOf(i)));
		}
		final Page<Flight> page = flightRepository.findAll(PageRequest.of(2, 5));
		assertThat(page.getTotalElements()).isEqualTo(50);
		assertThat(page.getNumberOfElements()).isEqualTo(5);
		assertThat(page.getTotalPages()).isEqualTo(10);
		assertThat(page.getContent())
			.extracting(Flight::getDestination)
			.containsExactly("10", "11", "12", "13", "14");
		
	}

	@Test
	public void shouldPageAndSortResults() {
		for (int i=0; i<50; i++) {
			flightRepository.save(createFlight(String.valueOf(i)));
		}
		final Page<Flight> page = flightRepository.findAll(PageRequest.of(2, 5, Sort.by(Order.desc("destination"))));
		assertThat(page.getTotalElements()).isEqualTo(50);
		assertThat(page.getNumberOfElements()).isEqualTo(5);
		assertThat(page.getTotalPages()).isEqualTo(10);
		assertThat(page.getContent())
			.extracting(Flight::getDestination)
			.containsExactly("44", "43", "42", "41", "40");
		
	}
	
	@Test
	public void shouldPageAndSortDerivedQuery() {
		for (int i=0; i<10; i++) {
			Flight flight = createFlight(String.valueOf(i));
			flight.setOrigin("Paris");
			flightRepository.save(flight);
		}
		for (int i=0; i<10; i++) {
			Flight flight = createFlight(String.valueOf(i));
			flight.setOrigin("London");
			flightRepository.save(flight);
		}

		final Page<Flight> page = flightRepository.findByOrigin("London", PageRequest.of(0, 5, Sort.by(Order.desc("destination"))));

		assertThat(page.getTotalElements()).isEqualTo(10);
		assertThat(page.getNumberOfElements()).isEqualTo(5);
		assertThat(page.getTotalPages()).isEqualTo(2);
		assertThat(page.getContent())
			.extracting(Flight::getDestination)
			.containsExactly("9", "8", "7", "6", "5");
		
	}
	
	private Flight createFlight(String destination) {
		final Flight flight = new Flight();
		flight.setOrigin("London");
		flight.setDestination(destination);
		flight.setScheduledAt(LocalDateTime.parse("2011-12-13T12:12:00"));
		
		return flight;
	}
	
	private Flight createFlight(String destination, LocalDateTime scheduledAt) {
		final Flight flight = new Flight();
		flight.setOrigin("London");
		flight.setDestination(destination);
		flight.setScheduledAt(scheduledAt);
		
		return flight;
	}	
}
