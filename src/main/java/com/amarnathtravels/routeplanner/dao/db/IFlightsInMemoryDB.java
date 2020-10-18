package com.amarnathtravels.routeplanner.dao.db;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface IFlightsInMemoryDB {
		boolean loadAllFlightsUsingCSVFile(XSSFWorkbook xssfWorkbook);
}
