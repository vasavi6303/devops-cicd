package com.myapp.spring.tdd.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

@SpringBootTest
public class FlightRepositoryProjectionQueryTest {
	
	ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

	@Test
	public void createMapBackedProjection() {

		Flight flight = factory.createProjection(Flight.class);
		flight.setAirlines("Airindia");
		flight.setDistance(578.5);

		// Verify accessors work
		assertEquals(flight.getAirlines(), "Airindia");
		assertEquals(flight.getDistance(), 578.5);

		
	}

	@Test
	public void createsProxyForSourceMap() {

		Map<String, Object> backingMap = new HashMap<>();
		backingMap.put("airlines", "Airindia");
		backingMap.put("distance", 578.5);

		Flight flight = factory.createProjection(Flight.class, backingMap);

		// Verify accessors work
		assertEquals(flight.getAirlines(), "Airindia");
		assertEquals(flight.getDistance(), 578.5);
	}

	interface Flight {

		String getAirlines();

		void setAirlines(String airlines);

		Double getDistance();

		void setDistance(Double distance);

		@Value("#{target.airlines + ' ' + target.distance}")
		String getAirlinesDistance();
	}

}
