package com.xlw.springdataoverview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xlw.springdataoverview.entity.Flight;
import com.xlw.springdataoverview.repository.FlightRepository;

import jakarta.transaction.Transactional;

@Component
public class FlightService {

	private FlightRepository flightRepository;
	
	public FlightService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}
	
	public void saveFlight(Flight flight) {
		flightRepository.save(flight);
		throw new RuntimeException("I failed");
	}
	
	@Transactional
	public void saveFlightTransactional(Flight flight) {
		flightRepository.save(flight);
		throw new RuntimeException("I failed");		
	}
	
}
