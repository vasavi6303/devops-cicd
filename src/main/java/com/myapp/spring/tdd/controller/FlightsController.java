package com.myapp.spring.tdd.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.spring.tdd.model.Flight;
import com.myapp.spring.tdd.service.FlightService;

@RestController
@RequestMapping("/flights")
public class FlightsController {
	
	@Autowired
	private FlightService flightService;
	
	@GetMapping
	public Iterable<Flight> getAllFlights(){
		return flightService.findAll();
	}
	@GetMapping("/{flightId}")
	public ResponseEntity<?> getFlight(@PathVariable("flightId")Integer flightId){
		return new ResponseEntity<Flight>( flightService.findFlightById(flightId),HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> saveFlight(@RequestBody Flight flight){
		return new ResponseEntity<Flight>( flightService.save(flight),HttpStatus.CREATED);
	}
	
	
	@PostMapping("/bulk")
	public ResponseEntity<?> saveBulkFlight(@RequestBody List<Flight> flights){
		return new ResponseEntity<Iterable<Flight>>( flightService.saveAll(flights),HttpStatus.CREATED);
	}
	
	@PutMapping("/{flightId}")
	public ResponseEntity<?> updateFlight(@PathVariable("flightId")Integer flightId,@RequestBody Flight flight) throws ErrorMessage{
		
	Flight existingflight=	flightService.findFlightById(flightId);
	if(existingflight == null) {
		throw new ErrorMessage("Flight With Id "+flightId+" Not Found");
	}
	BeanUtils.copyProperties(flight, existingflight);
		return new ResponseEntity<Flight>( flightService.update(existingflight),HttpStatus.OK);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler()
	public ErrorMessage exceptionHandler(ErrorMessage e){
		return new ErrorMessage(e.getLocalizedMessage());
	}

}
