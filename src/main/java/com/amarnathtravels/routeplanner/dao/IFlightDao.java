package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.model.flight.Flight;

import java.util.Map;

public interface IFlightDao {

		boolean saveAllFlights(Map<String, Flight> flightMap);
		Map<String, Flight> getAllFlightsMap();
}
