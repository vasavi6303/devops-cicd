package com.myapp.spring.tdd.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.spring.tdd.model.Flight;
import com.myapp.spring.tdd.repository.FlightRepository;

@Service
public class FlightServiceImpl implements FlightService {
	
	@Autowired
	private FlightRepository repository;

	@Override
	public Flight save(Flight flight) {
		// TODO Auto-generated method stub
		
		
		return repository.save(flight);
	}

	@Override
	public Flight update(Flight flight) {
		// TODO Auto-generated method stub
		
		Flight existingFlight=repository.findById(flight.getId()).get();
		BeanUtils.copyProperties(existingFlight, flight);
		repository.save(existingFlight);
		return existingFlight;
	}

	@Override
	public Flight findFlightById(Integer flightId) {
		// TODO Auto-generated method stub
		return repository.findFlightById(flightId);
	}

	@Override
	public void delete(Integer flightId) {
		// TODO Auto-generated method stub
		Flight existingFlight=repository.findById(flightId).get();
		if(existingFlight!=null)
			repository.delete(existingFlight);

	}

	@Override
	public Iterable<Flight> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Iterable<Flight> findAllByAirline(String airlines) {
		// TODO Auto-generated method stub
		return repository.findFlightByAirlines(airlines);
	}

	@Override
	public Iterable<Flight> saveAll(Iterable<Flight> flights) {
		// TODO Auto-generated method stub
		return repository.saveAll(flights);
	}

}
