package com.amarnathtravels.routeplanner.dao.route;

import com.amarnathtravels.routeplanner.model.flight.Airport;
import com.amarnathtravels.routeplanner.model.flight.Flight;
import com.amarnathtravels.routeplanner.model.flight.Status;
import lombok.Data;

@Data
public class FlightConnection extends Connection{
		Flight flight;
		Airport source;
		Airport dest;
		Status status;
}
