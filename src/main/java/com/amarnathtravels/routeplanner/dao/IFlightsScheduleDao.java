package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.model.flight.Flight;
import com.amarnathtravels.routeplanner.model.flight.FlightSchedule;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public interface IFlightsScheduleDao {
		boolean saveAllFlights(Map<String, Flight> flightMap);

}
