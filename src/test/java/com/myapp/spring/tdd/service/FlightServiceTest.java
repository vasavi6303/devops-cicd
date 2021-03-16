package com.myapp.spring.tdd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.myapp.spring.tdd.model.Flight;
import com.myapp.spring.tdd.repository.FlightRepository;

@SpringBootTest

// spring context
// environment
// loading the beans
// start the embeded tomcat server
public class FlightServiceTest {
	
	@Autowired
	private FlightService service;
	
	@MockBean
	private FlightRepository repository;
	
	
	
	
	@Test
	@DisplayName("Test Flight with id successfully")
	public void testFlightNotFoundForNonExistingId() {
		Flight mockFlight = new Flight();
		mockFlight.setId(1);
		mockFlight.setAirlines("Airindia");
		mockFlight.setDistance(678);
		
		doReturn(mockFlight).when(repository).findFlightById(1);
		
		Flight foundFlight=service.findFlightById(1);
		assertNotNull(foundFlight);
		assertSame("Airindia", foundFlight.getAirlines());
		
		
		
		
		
	}
	
	
	@Test
	@DisplayName("Fail to Find flight with id ")
	public void testFailToFindFlightById() {
		
		
		
		doReturn(null).when(repository).findFlightById(1);
		
		Flight foundFlight=service.findFlightById(1);
		assertNull(foundFlight);
	
		
		
		
		
		
	}
	
	@Test
	@DisplayName("Find All Flights ")
	public void testFindAllFlights() {
		
		
		Flight mockFlight1 = new Flight();
		mockFlight1.setId(1);
		mockFlight1.setAirlines("Airindia");
		mockFlight1.setDistance(678);
		
		
		Flight mockFlight2 = new Flight();
		mockFlight2.setId(2);
		mockFlight2.setAirlines("Indigo");
		mockFlight2.setDistance(978);
		
		doReturn(Arrays.asList(mockFlight1,mockFlight2)).when(repository).findAll();
		
		Iterable<Flight> flights=service.findAll();
		assertEquals(2,((Collection<?>)flights).size());
	
		
		
		
		
		
	}
	
	
//	@Test
//	@DisplayName("Test Flight not found for non existing id")
//	public void testFlightSavedSuccesfully() {
//		
//		// Prepare Mock Product
//		Flight flight = new Flight();
//		flight.setAirlines("Spicejet");
//		flight.setDistance(554);
//		
//		// given 4 flights in the database
//		
//		// when
//		Flight savedFlight=repository.save(flight);
//		
//		// then
//		
//		assertNotNull(savedFlight,"Flight should be saved");
//		assertNotNull(savedFlight.getId(),"Flight should have id after saving");
//		assertEquals(flight.getAirlines(),savedFlight.getAirlines());
//		
//		
//	}
	

}
