package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.model.bus.BusStand;

public interface IBusStandDao {
		BusStand findBusStandByCode(String stringCellValue);
}
