package com.xlw.springdataoverview.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xlw.springdataoverview.entity.Flight;

//public interface FlightRepository extends CrudRepository<Flight, Long> {
public interface FlightRepository extends CrudRepository<Flight, Long>, PagingAndSortingRepository<Flight, Long>, DeleteByOriginRepository {
	public List<Flight> findByOrigin(String origin);
	public Page<Flight> findByOrigin(String origin, Pageable pageRequest);
	public List<Flight> findByOriginIgnoreCase(String origin);
	public List<Flight> findByOriginAndDestination(String origin, String destination);
	public List<Flight> findByOriginIn(String ... origins);
}
