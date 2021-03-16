package com.myapp.spring.tdd.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.spring.tdd.model.Flight;

@SpringBootTest

// spring context
// environment
// loading the beans
// start the embeded tomcat server
public class FlightRepositoryTest {
	
	@Autowired
	private FlightRepository repository;
	
	private static File DATA_JSON=Paths.get("src", "test","resources","flights.json").toFile();
	
	@BeforeEach
	void setup() throws JsonParseException, JsonMappingException, IOException {
		
	Flight flights[]=	new ObjectMapper().readValue(DATA_JSON, Flight[].class);
	Arrays.stream(flights).forEach(repository::save);
		
	}
	
	@AfterEach
	
	void tearDown() {
		
		repository.deleteAll();
	}
	
	@Test
	@DisplayName("Test Flight not found for non existing id")
	public void testFlightNotFoundForNonExistingId() {
		
		// given 4 flights in the database
		
		// when
		Flight retrievedFlight=repository.findFlightById(99);
		
		// then
		
		assertNull(retrievedFlight, "Flight with id 99 should not exist");
		
		
	}
	
	@Test
	@DisplayName("Test Flight not found for non existing id")
	public void testFlightSavedSuccesfully() {
		
		// Prepare Mock Product
		Flight flight = new Flight();
		flight.setAirlines("Spicejet");
		flight.setDistance(554);
		flight.setId(80);
		
		// given 4 flights in the database
		
		// when
		Flight savedFlight=repository.save(flight);
		
		// then
		
		assertNotNull(savedFlight,"Flight should be saved");
		assertNotNull(savedFlight.getId(),"Flight should have id after saving");
		assertEquals(flight.getAirlines(),savedFlight.getAirlines());
		
		
	}
	

}
