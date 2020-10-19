package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.dao.db.GraphInMemoryDb;
import com.amarnathtravels.routeplanner.model.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class FlightDao implements IFlightDao{
		@Autowired GraphInMemoryDb graphInMemoryDb;
		@Override
		public boolean saveAllFlights(Map<String, Flight> flightMap) {
				return graphInMemoryDb.saveAllFlights(flightMap);
		}

		@Override
		public Map<String, Flight> getAllFlightsMap() {
				return graphInMemoryDb.getFlightMap();
		}

		@Override public Flight findFlightByCode(String flightCode) {
				return null;
		}
}
