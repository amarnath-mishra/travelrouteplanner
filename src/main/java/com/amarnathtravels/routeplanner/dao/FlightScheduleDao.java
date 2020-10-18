package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.model.flight.Flight;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class FlightScheduleDao implements IFlightsScheduleDao{
		@Override public boolean saveAllFlights(Map<String, Flight> flightMap) {
				return false;
		}
}
