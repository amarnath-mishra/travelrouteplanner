package com.amarnathtravels.routeplanner.dao.db;

import com.amarnathtravels.routeplanner.dao.route.*;
import com.amarnathtravels.routeplanner.model.bus.BusStand;
import com.amarnathtravels.routeplanner.model.flight.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GraphInMemoryDb implements ITravelInMemoryDB {
		//City -> List of TravelMode(Airport/Bus) -> Flight/Bus connection graph
		//And at last level, key is UniqueAirportCode/BusStationCode and connections as directed graph edge which are nothing but routes
		/***
		 * TODO:// Last Level should be List<Map<String, List<Connection>> because for e.g. one city can have multiple Bus Stands and Aiports
		 * but for now I am assuming each city will have only one airport and one bus stand, although we can have this use case with current Graph Structure also
		 */

		public Map<String, Map<TravelMode,Map<String, List<GraphNode>>>> graph =new HashMap<>();
		public Map<String, Airport> airportMap=new HashMap<>();
		public Map<String, Flight> flightMap=new HashMap<>();
		public Map<String, String> codeVsCityMap= new HashMap<>();


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
		public boolean saveRouteConnection(Connection connection) {
				TravelMode travelMode = connection.getTravelMode();
				if(travelMode==TravelMode.FLIGHT){
						FlightConnection flightConnection = (FlightConnection) connection;
						String srcAirportCode =flightConnection.getSource().getCode();
						String city = codeVsCityMap.get(srcAirportCode);
						graph.get(city).get(TravelMode.FLIGHT).get(srcAirportCode).add(	new GraphNode(flightConnection.getDest().getCode(),connection));
				} else {
						BusConnection busConnection = (BusConnection) connection;
						String srcBusStandCode =busConnection.getSource().getCode();
						String city = codeVsCityMap.get(srcBusStandCode);
						graph.get(city).get(TravelMode.FLIGHT).get(srcBusStandCode).add(new GraphNode(busConnection.getDest().getCode(),connection));
				}
				return true;
		}


		@Override
		public Map<String, List<GraphNode>> fetchGraphBasedOnSourceDestTravelModeAndDate(String src,
				String dest, TravelMode travelMode, LocalDateTime date) {
				/***:: For now I am returning the entire Graph, but we can assume that
				 * we can assume that we have applied these filters given in parameters of this function
				 * and can return a very shorter Map with lesser nodes,
				 * so that having them in memory and apply algorithms/operations, wont cause any issue
				 */

				Map<String, List<GraphNode>> clippedGraph = new HashMap<>();
				graph.entrySet().stream().map(Map.Entry::getValue).forEach(travelModeMap -> {
						travelModeMap.entrySet().stream().filter(map->map.getKey().equals(travelMode)).map(Map.Entry::getValue).forEach(codeVsConnection->{
								codeVsConnection.entrySet().stream().forEach(node->{
										if(clippedGraph.get(node.getKey())==null){
												clippedGraph.put(node.getKey(), new LinkedList<>());
										}
										clippedGraph.get(node.getKey()).addAll(node.getValue());
								});
						});
				});
				clippedGraph.entrySet().forEach(node->{
						clippedGraph.put(node.getKey(), node.getValue().stream().filter(i->i.getConnection().getDeptTime().isAfter(date) && i.getConnection().getDeptTime().isBefore(date.plusDays(2))).collect(
								Collectors.toCollection(ArrayList::new)));
				});
//				String city = codeVsCityMap.get(src);
//				return graph.get(city).get(travelMode);
				return clippedGraph;
		}

		@Override public boolean saveAirport(Airport airport, String city) {
				if(graph.get(city)==null){
						Map<TravelMode,Map<String, List<GraphNode>>> travelModeGraph = new HashMap<>();
						graph.put(city, travelModeGraph );
				}
				if(graph.get(city).get(TravelMode.FLIGHT)==null){
						Map<String, List<GraphNode>> airportGraph = new HashMap<>();
						graph.get(city).put(TravelMode.FLIGHT, airportGraph);
				}
				graph.get(city).get(TravelMode.FLIGHT).put(airport.getCode(),new LinkedList<>());
				return true;
		}

		@Override public boolean saveBusStand(BusStand busStand) {
				String city = busStand.getCode();
				if(graph.get(city)==null){
						Map<TravelMode,Map<String, List<GraphNode>>> travelModeGraph = new HashMap<>();
						graph.put(city, travelModeGraph );
				}
				if(graph.get(city).get(TravelMode.BUS)==null){
						Map<String, List<GraphNode>> airportGraph = new HashMap<>();
						graph.get(city).put(TravelMode.BUS, airportGraph);
				}
				if(graph.get(city).get(TravelMode.BUS).get(busStand.getCode())==null){
						graph.get(city).get(TravelMode.BUS).put(busStand.getCode(),new LinkedList<>());
				}
				return true;
		}

//		@Override
//		public boolean loadGraphConnections(
//				Map<String, Map<String, Map<String, List<GraphNode>>>> graph) {
//				this.graph = graph;
//				return false;
//		}



		@PostConstruct
		private void init(){
				//flightSchedules.
		}

		@Override
		public Map<String, Map<TravelMode, Map<String, List<GraphNode>>>> getGraph() {
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
