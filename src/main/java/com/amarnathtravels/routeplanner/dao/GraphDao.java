package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.dao.db.ITravelInMemoryDB;
import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.dao.route.GraphNode;
import com.amarnathtravels.routeplanner.dao.route.TravelMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Component
public class GraphDao implements IGraphDao{
		@Autowired
		ITravelInMemoryDB iTravelInMemoryDB;
//		@Override
//		public boolean saveGraph(Map<String, Map<String, Map<String, List<Connection>>>> graph) {
//				return iTravelInMemoryDB.loadGraphConnections(graph);
//		}

		@Override
		public Map<String, String> getCodeVsCityMap() {
				return iTravelInMemoryDB.getCodeVsCityMap();
		}

		@Override
		public Map<String, List<GraphNode>> fetchGraphBasedOnSourceDestTravelModeAndDate(String src,
				String dest, TravelMode travelMode, LocalDateTime date) {
				return iTravelInMemoryDB.fetchGraphBasedOnSourceDestTravelModeAndDate(src, dest, travelMode, date);
		}

		@Override
		public boolean saveRouteConnection(Connection connection) {
				return iTravelInMemoryDB.saveRouteConnection(connection);
		}

}
