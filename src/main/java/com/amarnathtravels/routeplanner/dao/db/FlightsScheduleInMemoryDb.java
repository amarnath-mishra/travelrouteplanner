package com.amarnathtravels.routeplanner.dao.db;

import com.amarnathtravels.routeplanner.dao.IFlightsScheduleDao;
import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.model.flight.Airport;
import com.amarnathtravels.routeplanner.model.flight.FlightSchedule;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FlightsScheduleInMemoryDb implements IFlightsScheduleDao {
		Map<Airport, Connection> graph = new HashMap<>();

		@PostConstruct
		private void init(){
				//flightSchedules.
		}

		@Override
		public boolean saveAllFlights(Object T) {
				return false;
		}
}
