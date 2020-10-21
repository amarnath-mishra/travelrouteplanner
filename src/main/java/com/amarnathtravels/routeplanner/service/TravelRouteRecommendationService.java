package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.dao.IGraphDao;
import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.dao.route.GraphNode;
import com.amarnathtravels.routeplanner.dao.route.TravelMode;
import com.amarnathtravels.routeplanner.exception.DestNotReachableException;
import com.amarnathtravels.routeplanner.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
		public List<Node> getLeastTimeTakenRoute(String src, String dest,String travelMode1, LocalDateTime date)
				throws DestNotReachableException {
				TravelMode travelMode = TravelMode.valueOf(travelMode1);
				Map<String, List<GraphNode>> graph = graphDao.fetchGraphBasedOnSourceDestTravelModeAndDate(src, dest, travelMode, date);
				List<Node> paths= getPathsOfShortestDurationToReachDestination( graph, src, dest, travelMode, date);
				return paths;
		}

		@Override public String hello() {
				return null;
		}

		/***
		 * Currently I am applying standard Dijkstra Algorithm to get shortest path and returning that only
		 * but we can modify this function to return different results as well, like return Top K results using K shortest Paths etc
		 * and in that case it should return List<List<Connection>>.
		 */
		private List<Node> getPathsOfShortestDurationToReachDestination(Map<String, List<GraphNode>> graph,
				String src, String dest, TravelMode travelMode, LocalDateTime date) throws DestNotReachableException {
				ShortestPathUsingDijkstra shortestPathUsingDijkstra = new ShortestPathUsingDijkstra(graph.size());
				List<Node> shortestPath = shortestPathUsingDijkstra.getShortestPath(graph, src, dest, travelMode, date);
				if(shortestPath==null){
						throw new DestNotReachableException("No "+  travelMode.name() + "S for given requests");
				}
				return shortestPath;
		}


}
