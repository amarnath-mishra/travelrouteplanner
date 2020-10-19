package com.amarnathtravels.routeplanner.dao.route;

import com.amarnathtravels.routeplanner.model.bus.Bus;
import com.amarnathtravels.routeplanner.model.bus.BusStand;
import com.amarnathtravels.routeplanner.model.flight.Status;
import lombok.Data;

@Data
public class BusConnection extends Connection{
		Bus bus;
		BusStand source;
		BusStand dest;
		Status status;

}
