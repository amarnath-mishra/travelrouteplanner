package com.amarnathtravels.routeplanner.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface IExcelUploadService {
		boolean loadAirportsUsingExcel(XSSFWorkbook workbook);
		boolean loadFlightsUsingExcel(XSSFWorkbook workbook);
		boolean loadGraphUsingExcel(XSSFWorkbook workbook);
}
