package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.dao.route.TravelMode;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IGraphDao {
		boolean saveGraph(Map<String, Map<String, Map<String, List<Connection>>>> graph);

		Map<String, String> getCodeVsCityMap();

		public Map<String, List<Connection>> fetchGraphBasedOnSourceDestTravelModeAndDate(String src, String dest,
				TravelMode travelMode, Date date);
}
