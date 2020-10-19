package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.model.bus.BusStand;

public interface IBusStandDao {
		BusStand findBusStandByCode(String stringCellValue);
}
