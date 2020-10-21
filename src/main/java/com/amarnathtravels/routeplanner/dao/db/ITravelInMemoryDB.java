package com.amarnathtravels.routeplanner.dao.db;

import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.dao.route.GraphNode;
import com.amarnathtravels.routeplanner.dao.route.TravelMode;
import com.amarnathtravels.routeplanner.model.bus.BusStand;
import com.amarnathtravels.routeplanner.model.flight.Airport;
import com.amarnathtravels.routeplanner.model.flight.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ITravelInMemoryDB {
		boolean saveAirport(Airport airport, String city);
		boolean saveRouteConnection(Connection connection);
		boolean saveBusStand(BusStand busStand);
		Flight findFlightByCode(String flightCode);
		Airport findAirportByCode(String airportCode);
		boolean saveAllFlights(Map<String, Flight> flightMap);
		Map<String, Map<TravelMode, Map<String, List<GraphNode>>>> getGraph();
		Map<String, Airport> getAirportMap();
		Map<String, Flight> getFlightMap();
		Map<String, String> getCodeVsCityMap();
		boolean saveAllAirports(Map<String, Airport> airportMap);
//		boolean loadGraphConnections(Map<String, Map<TravelMode, Map<String, List<GraphNode>>>> graph);

		Map<String, List<GraphNode>> fetchGraphBasedOnSourceDestTravelModeAndDate(String src, String dest, TravelMode travelMode, LocalDateTime date);

}
