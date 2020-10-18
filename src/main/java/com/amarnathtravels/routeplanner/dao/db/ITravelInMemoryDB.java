package com.amarnathtravels.routeplanner.dao.db;

import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.model.flight.Airport;
import com.amarnathtravels.routeplanner.model.flight.Flight;

import java.util.Map;

public interface ITravelInMemoryDB {
		boolean saveAllFlights(Map<String, Flight> flightMap);
		Map<String, Connection> getGraph();
		Map<String, Airport> getAirportMap();
		Map<String, Flight> getFlightMap();
}
