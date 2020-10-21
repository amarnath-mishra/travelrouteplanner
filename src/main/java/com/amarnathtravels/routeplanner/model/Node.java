package com.amarnathtravels.routeplanner.model;

import com.amarnathtravels.routeplanner.model.flight.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
		private String code;
		private String flightCode;
		private String busCode;
		private Long costInMinutes;
		private LocalDateTime lastDepartureTime;
		private LocalDateTime lastArrivalTime;
}
