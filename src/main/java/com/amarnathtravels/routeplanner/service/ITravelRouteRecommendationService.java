package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.dao.route.TravelMode;
import com.amarnathtravels.routeplanner.exception.DestNotReachableException;
import com.amarnathtravels.routeplanner.model.Node;

import java.time.LocalDateTime;
import java.util.List;

public interface ITravelRouteRecommendationService {

		List<Node> getLeastTimeTakenRoute(String src, String dest, String travelMode, LocalDateTime date)
				throws DestNotReachableException;

		String hello();
}
