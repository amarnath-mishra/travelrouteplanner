package com.amarnathtravels.routeplanner.model.flight;

import lombok.Data;

@Data
public class Flight {

		private String flightNo;
		private Airport source;
		private Airport dest;
		private float duration;

//		private List<FlightSeat> seats;

}
