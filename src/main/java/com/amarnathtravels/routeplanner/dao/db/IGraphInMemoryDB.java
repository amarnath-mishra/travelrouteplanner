package com.amarnathtravels.routeplanner.dao.db;

import com.amarnathtravels.routeplanner.dao.route.Connection;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public interface IGraphInMemoryDB {
		boolean loadAllFlightsUsingCSVFile(XSSFWorkbook xssfWorkbook);

		boolean loadGraphConnections(Map<String, Map<String, List<Map<String, List<Connection>>>>> graph);
}
