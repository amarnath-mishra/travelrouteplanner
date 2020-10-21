package com.amarnathtravels.routeplanner.controller;

import com.amarnathtravels.routeplanner.exception.DestNotReachableException;
import com.amarnathtravels.routeplanner.model.Node;
import com.amarnathtravels.routeplanner.service.ITravelRouteRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller public class RecommendationController {
		@Autowired ITravelRouteRecommendationService iTravelRouteRecommendationService;

		@GetMapping(value = "/recommendFlight/{src}/{dest}/{travelMode}/{date}")
		public @ResponseBody List<Node> recommendRoutes(
				@PathVariable String src, @PathVariable String dest,@PathVariable String travelMode,
				@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date)
				throws DestNotReachableException {

				return iTravelRouteRecommendationService.getLeastTimeTakenRoute(src, dest, travelMode,date);
		}

		@GetMapping("/hello2")
		@ResponseBody
		public String hello(){
				return iTravelRouteRecommendationService.hello();
		}
}
