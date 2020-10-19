package com.amarnathtravels.routeplanner.model.bus;

import lombok.Data;

import java.util.List;

@Data
public class BusStand {
		private String name;
		private String address; //TODO: Make a address class
		private String code;
		private List<Bus> busList;
		private BusStandType busStandType;

}
