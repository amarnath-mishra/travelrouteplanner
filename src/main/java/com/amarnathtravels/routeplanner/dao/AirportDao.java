package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.dao.db.FlightsScheduleInMemoryDb;
import com.amarnathtravels.routeplanner.model.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class AirportDao implements IAirportDao{
		@Autowired
		FlightsScheduleInMemoryDb flightsScheduleInMemoryDb;
		@Override
		public Map<String, Flight> getAllAirportsMap() {
				return flightsScheduleInMemoryDb.getAirportMap();
		}
}
