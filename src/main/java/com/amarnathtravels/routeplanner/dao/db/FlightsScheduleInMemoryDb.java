package com.amarnathtravels.routeplanner.dao.db;

import com.amarnathtravels.routeplanner.dao.IFlightsScheduleDao;
import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.model.BookingStatus;
import com.amarnathtravels.routeplanner.model.flight.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class FlightsScheduleInMemoryDb implements ITravelInMemoryDB {
		//Storing key as UniqueAirportCode and connections as directed graph edge which are nothing but routes
		private Map<String, Connection> graph = new HashMap<>();
		private Map<String, Airport> airportMap = new HashMap<>();
		private Map<String, Flight> flightMap = new HashMap<>();

		public Map<String, Connection> getGraph() {
				return graph;
		}

		public Map<String, Airport> getAirportMap() {
				return airportMap;
		}

		public Map<String, Flight> getFlightMap() {
				return flightMap;
		}

		@Override
		public Flight findFlightByCode(String flightCode) {
				return flightMap.get(flightCode);
		}

		@Override
		public Airport findAirportByCode(String airportCode) {
				return airportMap.get(airportCode);
		}

		@Override
		public boolean saveAllAirports(Map<String, Airport> airportMap) {
				this.airportMap=airportMap;
				return true;
		}

		@PostConstruct
		private void init(){
				//flightSchedules.
		}

		@Override
		public boolean saveAllFlights(Map<String, Flight> flightMap) {
				this.flightMap=flightMap;
				return true;
		}
}
