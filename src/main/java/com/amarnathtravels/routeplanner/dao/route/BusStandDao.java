package com.amarnathtravels.routeplanner.dao.route;

import com.amarnathtravels.routeplanner.dao.IBusStandDao;
import com.amarnathtravels.routeplanner.model.bus.BusStand;
import org.springframework.stereotype.Component;

@Component
public class BusStandDao implements IBusStandDao {
		@Override public BusStand findBusStandByCode(String stringCellValue) {
				return null;
		}
}
