package com.myapp.spring.tdd.service;

import com.myapp.spring.tdd.model.Flight;

public interface FlightService {

	
	Flight save(Flight flight);
	Iterable<Flight> saveAll(Iterable<Flight> flights);
	Flight update(Flight flight);
	Flight findFlightById(Integer flightId);
	void delete(Integer flightId);
	Iterable<Flight> findAll();
	
	Iterable<Flight> findAllByAirline(String airlines);
	
	
	
}
