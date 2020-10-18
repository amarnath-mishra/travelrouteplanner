package com.amarnathtravels.routeplanner.model.flight;

import lombok.Data;

import java.util.List;

@Data
public class Flight {

		private String flightNo;
		private Airport source;
		private Airport dest;
		private float duration;
		private List<FlightSeat> seats;

}
