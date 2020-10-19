package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.dao.route.Connection;

import java.util.Date;
import java.util.List;

public interface ITravelRouteRecommendationService {

		List<Connection> getLeastTimeTakenRoute(String src, String dest,String travelMode, Date date);
}
