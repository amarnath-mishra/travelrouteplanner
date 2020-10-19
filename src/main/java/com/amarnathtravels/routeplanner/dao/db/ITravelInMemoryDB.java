package com.amarnathtravels.routeplanner.dao.db;

import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.dao.route.TravelMode;
import com.amarnathtravels.routeplanner.model.flight.Airport;
import com.amarnathtravels.routeplanner.model.flight.Flight;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ITravelInMemoryDB {
		Flight findFlightByCode(String flightCode);
		Airport findAirportByCode(String airportCode);
		boolean saveAllFlights(Map<String, Flight> flightMap);
		Map<String, Map<String, Map<String, List<Connection>>>> getGraph();
		Map<String, Airport> getAirportMap();
		Map<String, Flight> getFlightMap();
		Map<String, String> getCodeVsCityMap();
		boolean saveAllAirports(Map<String, Airport> airportMap);
		boolean loadGraphConnections(Map<String, Map<String, Map<String, List<Connection>>>> graph);

		Map<String, List<Connection>> fetchGraphBasedOnSourceDestTravelModeAndDate(String src, String dest, TravelMode travelMode, Date date);

}
