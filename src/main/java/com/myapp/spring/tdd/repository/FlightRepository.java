package com.myapp.spring.tdd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myapp.spring.tdd.model.Flight;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Integer>{

	
	Iterable<Flight> findFlightByAirlines(String airlines);
	
	Flight findFlightById(Integer id);
}
