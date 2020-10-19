package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.dao.IGraphDao;
import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.dao.route.TravelMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TravelRouteRecommendationService implements ITravelRouteRecommendationService{
		@Autowired
		IGraphDao graphDao;

		/*** Currently I am only returni
		 *
		 * @return
		 */
		@Override
		public List<Connection> getLeastTimeTakenRoute(String src, String dest,String travelMode1, Date date) {
				TravelMode travelMode = TravelMode.valueOf(travelMode1);
				Map<String, List<Connection>> graph = graphDao.fetchGraphBasedOnSourceDestTravelModeAndDate(src, dest, travelMode, date);
				List<Connection> paths= getPathsOfShortestDurationToReachDestination(graph, src, dest, travelMode, date);
				return paths;
		}

		/***
		 * Currently I am applying standard Dijkstra Algorithm to get shortest path and returning that only
		 * but we can modify this function to return different results as well, like return Top K results using K shortest Paths etc
		 * and in that case it should return List<List<Connection>>.
		 */
		private List<Connection> getPathsOfShortestDurationToReachDestination(Map<String, List<Connection>> graph,
				String src, String dest, TravelMode travelMode, Date date) {


		}
}
