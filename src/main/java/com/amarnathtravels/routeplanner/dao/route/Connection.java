package com.amarnathtravels.routeplanner.dao.route;

import com.amarnathtravels.routeplanner.model.flight.Airport;
import com.amarnathtravels.routeplanner.model.flight.Flight;
import com.amarnathtravels.routeplanner.model.flight.Price;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Connection {
		Flight flight;
		Airport source;
		Airport dest;
		LocalDateTime deptTime;
		LocalDateTime arrivalTime;
		Connection type;
		Price price;

}
