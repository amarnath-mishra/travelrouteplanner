package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.dao.route.GraphNode;
import com.amarnathtravels.routeplanner.dao.route.TravelMode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IGraphDao {
//		boolean saveGraph(Map<String, Map<String, Map<String, List<Connection>>>> graph);

		Map<String, String> getCodeVsCityMap();

		Map<String, List<GraphNode>> fetchGraphBasedOnSourceDestTravelModeAndDate(String src, String dest,
				TravelMode travelMode, LocalDateTime date);
		boolean saveRouteConnection(Connection connection);
}
