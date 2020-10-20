package com.amarnathtravels.routeplanner.dao.db;

import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.dao.route.GraphNode;
import com.amarnathtravels.routeplanner.dao.route.TravelMode;
import com.amarnathtravels.routeplanner.model.flight.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class GraphInMemoryDb implements ITravelInMemoryDB {
		//City -> List of TravelMode(Airport/Bus) -> Flight/Bus connection graph
		//And at last level, key is UniqueAirportCode/BusStationCode and connections as directed graph edge which are nothing but routes
		/***
		 * TODO:// Last Level should be List<Map<String, List<Connection>> because for e.g. one city can have multiple Bus Stands and Aiports
		 * but for now I am assuming each city will have only one airport and one bus stand, although we can have this use case with current Graph Structure also
		 */

		private Map<String, Map<String,Map<String, List<GraphNode>>>> graph;
		private Map<String, Airport> airportMap;
		private Map<String, Flight> flightMap;
		private Map<String, String> codeVsCityMap= new HashMap<>();


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
		public Map<String, List<GraphNode>> fetchGraphBasedOnSourceDestTravelModeAndDate(String src,
				String dest, TravelMode travelMode, Date date) {
				/***:: For now I am returning the entire Graph, but we can assume that
				 * we can assume that we have applied these filters given in parameters of this function
				 * and can return a very shorter Map with lesser nodes,
				 * so that having them in memory and apply algorithms/operations, wont cause any issue
				 */
				String city = codeVsCityMap.get(src);
				return graph.get(city).get(travelMode);
		}

		@Override
		public boolean loadGraphConnections(
				Map<String, Map<String, Map<String, List<GraphNode>>>> graph) {
				this.graph = graph;
				return false;
		}



		@PostConstruct
		private void init(){
				//flightSchedules.
		}

		@Override
		public Map<String, Map<String, Map<String, List<GraphNode>>>> getGraph() {
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

		@Override public Map<String, String> getCodeVsCityMap() {
				return codeVsCityMap;
		}

		@Override
		public boolean saveAllFlights(Map<String, Flight> flightMap) {
				this.flightMap=flightMap;
				return true;
		}
}
