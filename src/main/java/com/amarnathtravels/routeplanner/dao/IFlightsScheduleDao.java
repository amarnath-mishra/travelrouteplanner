package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.model.flight.Flight;
import java.util.Map;

public interface IFlightsScheduleDao {
		boolean saveAllFlights(Map<String, Flight> flightMap);

}
