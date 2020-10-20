package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.model.flight.Airport;
import com.amarnathtravels.routeplanner.model.flight.Flight;

import java.util.Map;

public interface IAirportDao {
		Map<String, Airport> getAllAirportsMap();
		Airport findAirportByCode(String airportCode);
		boolean saveAllAirports(Map<String, Airport> airportMap);
		boolean saveAirport(Airport airport);
}
