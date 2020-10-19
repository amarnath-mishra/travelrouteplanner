package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.model.bus.Bus;

public interface IBusDao {
		Bus findBusByCode(String stringCellValue);
}
