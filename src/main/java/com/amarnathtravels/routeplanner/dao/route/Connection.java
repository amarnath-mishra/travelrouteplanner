package com.amarnathtravels.routeplanner.dao.route;

import com.amarnathtravels.routeplanner.model.flight.FlightStatus;
import com.amarnathtravels.routeplanner.model.flight.TicketPrice;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Connection {
		String connId;
		LocalDateTime deptTime;
		LocalDateTime arrivalTime;
		ConnectionType type;
		TicketPrice ticketPrice;
}
