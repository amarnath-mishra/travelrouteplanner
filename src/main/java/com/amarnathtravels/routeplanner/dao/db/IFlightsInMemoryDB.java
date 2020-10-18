package com.amarnathtravels.routeplanner.dao.db;

import com.amarnathtravels.routeplanner.model.flight.FlightSchedule;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface IFlightsInMemoryDB {
		boolean loadAllFlightsUsingCSVFile(XSSFWorkbook xssfWorkbook);
}
