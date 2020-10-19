package com.amarnathtravels.routeplanner.dao.db;

import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.model.flight.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class GraphInMemoryDb implements ITravelInMemoryDB {
		//City, aiportCode , BusCode
		//City -> List of TravelMode(Airport/Bus) -> List of Flight/Bus connection
		//And at last level, key is UniqueAirportCode/BusStationCode and connections as directed graph edge which are nothing but routes
		private Map<String, Map<String,List<Map<String, List<Connection>>>>> graph;
		private Map<String, Airport> airportMap;
		private Map<String, Flight> flightMap;
		private Map<String, String> codeVsCityMap= new HashMap<>();


		@Override
		public Map<String, Map<String, List<Map<String, List<Connection>>>>> getGraph() {
				return graph;
		}
		@Override
		public Map<String, Airport> getAirportMap() {
				return airportMap;
		}
		@Override
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

		@Override
		public boolean loadGraphConnections(
				Map<String, Map<String, List<Map<String, List<Connection>>>>> graph) {
				this.graph = graph;
				return false;
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
