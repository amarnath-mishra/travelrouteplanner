package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.dao.db.FlightsScheduleInMemoryDb;
import com.amarnathtravels.routeplanner.model.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class FlightDao implements IFlightDao{
		@Autowired
		FlightsScheduleInMemoryDb flightsScheduleInMemoryDb;
		@Override
		public boolean saveAllFlights(Map<String, Flight> flightMap) {
				return flightsScheduleInMemoryDb.saveAllFlights(flightMap);
		}

		@Override
		public Map<String, Flight> getAllFlightsMap() {
				return flightsScheduleInMemoryDb.getFlightMap();
		}
}
