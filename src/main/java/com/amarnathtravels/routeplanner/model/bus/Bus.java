package com.amarnathtravels.routeplanner.model.bus;

import com.amarnathtravels.routeplanner.model.flight.FlightSeat;
import lombok.Data;

import java.util.List;

@Data
public class Bus {
		private String busNo;
		private List<BusSeat> seats;
}
