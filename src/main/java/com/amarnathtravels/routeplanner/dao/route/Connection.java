package com.amarnathtravels.routeplanner.dao.route;

import com.amarnathtravels.routeplanner.model.flight.Status;
import com.amarnathtravels.routeplanner.model.flight.TicketPrice;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Connection {
		TravelMode travelMode;
		String connId;
		LocalDateTime deptTime;
		LocalDateTime arrivalTime;
		ConnectionType type;
		TicketPrice ticketPrice;

}
