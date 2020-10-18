package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.dao.db.FlightsScheduleInMemoryDb;
import com.amarnathtravels.routeplanner.model.flight.Airport;
import com.amarnathtravels.routeplanner.model.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class AirportDao implements IAirportDao{
		@Autowired
		FlightsScheduleInMemoryDb flightsScheduleInMemoryDb;
		@Override
		public Map<String, Airport> getAllAirportsMap() {
				return flightsScheduleInMemoryDb.getAirportMap();
		}

		@Override public Airport findAirportByCode(String airportCode) {
				return flightsScheduleInMemoryDb.findAirportByCode(airportCode);
		}

		@Override public boolean saveAllAirports(Map<String, Airport> airportMap) {
				return flightsScheduleInMemoryDb.saveAllAirports(airportMap);
		}
}
