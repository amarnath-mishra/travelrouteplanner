package com.amarnathtravels.routeplanner.model.flight;

import lombok.Data;

import java.util.List;

@Data
public class Airport {
		private String name;
		private String address; //TODO: Make a address class
		private String code;
		private List<Flight> flightList;

}
