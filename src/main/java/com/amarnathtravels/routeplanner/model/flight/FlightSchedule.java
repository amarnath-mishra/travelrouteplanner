package com.amarnathtravels.routeplanner.model.flight;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightSchedule {
		Flight flight;
		private LocalDateTime departureTime;
		private LocalDateTime arrivalTime;
		private String Gate;
		private Status status;
}
