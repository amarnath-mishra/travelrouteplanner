package com.amarnathtravels.routeplanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
		private String code;
		private Long costInMinutes;
		private LocalDateTime lastFlightArrivalTime;
}
