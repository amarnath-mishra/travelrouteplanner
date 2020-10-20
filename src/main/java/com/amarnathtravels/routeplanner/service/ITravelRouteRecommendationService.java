package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.model.Node;

import java.time.LocalDateTime;
import java.util.List;

public interface ITravelRouteRecommendationService {

		List<Node> getLeastTimeTakenRoute(String src, String dest,String travelMode, LocalDateTime date);
}
