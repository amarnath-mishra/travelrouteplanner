package com.amarnathtravels.routeplanner.model.flight;

import lombok.Data;

import java.util.List;

@Data
public class Flight {

		private String flightNo;
		private List<FlightSeat> seats;

		//TODO:Other flight related Specs here we can have

}
