package com.myapp.spring.tdd.integration;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.spring.tdd.model.Flight;
import com.myapp.spring.tdd.repository.FlightRepository;
import com.myapp.spring.tdd.service.FlightService;

@SpringBootTest

// spring context
// environment
// loading the beans
// start the embeded tomcat server
@AutoConfigureMockMvc
public class FlightServiceIntegrationTest {
	
	@Autowired
	private FlightRepository repository;
	
	@Autowired
	private MockMvc mockMvc;
	
	
private static File DATA_JSON=Paths.get("src", "test","resources","flights.json").toFile();

	@BeforeEach
	void setup() throws JsonParseException, JsonMappingException, IOException {
		
		
		Flight flights[]=new ObjectMapper().readValue(DATA_JSON, Flight[].class);
		
	
		Arrays.stream(flights).forEach(repository::save);
	}
	
	@AfterEach
	void tearDown() {
		repository.deleteAll();
	}
	
	
	
	@Test
	@DisplayName("Test Flight with id successfully - GET /flights")
	public void testGetFlightById() throws Exception{
		
		
		
		mockMvc.perform(MockMvcRequestBuilders.get("/flights/{id}",5))
		.andExpect(status().isOk())
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		// validate response body
		
		
		//{"id":1,"airlines":"Airindia","distance":789}
		
		.andExpect(jsonPath("$.id", is(5)))
		.andExpect(jsonPath("$.airlines", is("Airindia")))
		.andExpect(jsonPath("$.distance", is(378)));
		
		
		
		
	}
	
	
	
	
	@Nested
	@DisplayName("Given List of flights in the db")
	class FindAll{
	
	@Test
	@DisplayName("when get request for all flights are made using GET /flights ")
	public void testFindAllFlights()throws Exception {
		
	
		mockMvc.perform(MockMvcRequestBuilders.get("/flights"))
		.andExpect(status().isOk())
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.*",isA(ArrayList.class)))
		
		// validate response body
		
		
		//{"id":1,"airlines":"Airindia","distance":789}
		
		
		.andExpect(jsonPath("$[*].airlines", hasItems("Airindia","Indigo","Airindia","Indigo")))
		.andExpect(jsonPath("$[*].id", hasItems(1,2,5,7)));
		
		
		
		
		
	}
	
	}
	@Test
	@DisplayName("Add New Flight  -POST /flights")
	public void testAddNewFlight()throws Exception {
		
		// Prepare Mock Product
		Flight mockflight = new Flight();
		mockflight.setId(24);
		mockflight.setAirlines("Spicejet");
		mockflight.setDistance(554);
		
		mockMvc.perform(post("/flights").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(mockflight)))
		
		.andExpect(status().isCreated())
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.airlines",is("Spicejet")));
		
		
	}
	
	@Test
	@DisplayName("Add New Bulk Flights  -POST /flights")
	public void testAddNewBulkFlights()throws Exception {
		
		// Prepare Mock Flight
		
		
		Flight newflight1 = new Flight();
		newflight1.setId(8);
		
		newflight1.setAirlines("Spicejet");
		newflight1.setDistance(554);
		
		Flight newflight2 = new Flight();
		newflight2.setId(9);
		newflight2.setAirlines("Vistara");
		newflight2.setDistance(454);
		List<Flight> newFlights = Arrays.asList(newflight1,newflight2);
		
		mockMvc.perform(post("/flights/bulk").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(newFlights)))
		
		.andExpect(status().isCreated())
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$[*].airlines", hasItems("Spicejet","Vistara")));
		
		
	}
	
	
	@Test
	@DisplayName("Update an existing Flight   -PUT /flights")
	public void testUpdateExistingFlight()throws Exception {
		
		
		
		Flight flightToBeUpdated = new Flight();
		flightToBeUpdated.setId(1);
		flightToBeUpdated.setAirlines("Vistara");
		flightToBeUpdated.setDistance(854);
		
		
		
		mockMvc.perform(put("/flights/{id}",1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(flightToBeUpdated)))
		
		.andExpect(status().isOk())
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.distance",is(854)));
		
		
	}
	
	@Test
	@DisplayName("Update an existing Flight  should raise a 404 not found  -PUT /flights")
	public void testFlightNotFoundWhileUpdating()throws Exception {
		
		// Prepare Mock Product
		Flight mockflight = new Flight();
		mockflight.setId(10);
		mockflight.setAirlines("Spicejet");
		mockflight.setDistance(554);
		
		
		
		
		
		mockMvc.perform(put("/flights/{id}",199).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(mockflight)))
		
		.andExpect(status().is4xxClientError());
		
		
	}

}
