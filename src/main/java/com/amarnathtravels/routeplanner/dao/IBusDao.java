package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.model.bus.Bus;

public interface IBusDao {
		Bus findBusByCode(String stringCellValue);
}
