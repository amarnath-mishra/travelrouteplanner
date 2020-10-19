package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.dao.db.GraphInMemoryDb;
import com.amarnathtravels.routeplanner.model.flight.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class AirportDao implements IAirportDao{
		@Autowired GraphInMemoryDb graphInMemoryDb;
		@Override
		public Map<String, Airport> getAllAirportsMap() {
				return graphInMemoryDb.getAirportMap();
		}

		@Override public Airport findAirportByCode(String airportCode) {
				return graphInMemoryDb.findAirportByCode(airportCode);
		}

		@Override public boolean saveAllAirports(Map<String, Airport> airportMap) {
				return graphInMemoryDb.saveAllAirports(airportMap);
		}
}
