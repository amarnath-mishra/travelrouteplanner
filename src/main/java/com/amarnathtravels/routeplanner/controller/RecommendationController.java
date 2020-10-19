package com.amarnathtravels.routeplanner.controller;

import com.amarnathtravels.routeplanner.service.ITravelRouteRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class RecommendationController {
		@Autowired
		ITravelRouteRecommendationService iTravelRouteRecommendationService;
		@GetMapping(value = "/recommendFlight/{src}/{dest}/{date}")
		public @ResponseBody ResponseEntity<Object> recommendRoutes( @RequestParam String src,@RequestParam String dest,
				@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {



				return iTravelRouteRecommendationService.getLeastTimeTakenRoute(src, dest, date);
		}
}
