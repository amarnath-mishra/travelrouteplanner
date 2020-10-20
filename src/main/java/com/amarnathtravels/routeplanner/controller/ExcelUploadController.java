package com.amarnathtravels.routeplanner.controller;

import com.amarnathtravels.routeplanner.service.IExcelUploadService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ExcelUploadController {
		private final IExcelUploadService iExcelUploadService;
		@PostMapping("/uploadFlights")
		@ResponseBody
		public Boolean uploadFlightsToDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws
				IOException {
				XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
				iExcelUploadService.loadFlightsUsingExcel(workbook);
				return true;
		}

		@PostMapping("/uploadAirports")
		@ResponseBody
		public Boolean uploadAirportsToDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws
				IOException {
				XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
				iExcelUploadService.loadAirportsUsingExcel(workbook);
				return true;
		}

		@PostMapping("/uploadFlightSchedules")
		@ResponseBody
		public Boolean uploadFlightSchedulesToDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws
				IOException {
				XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
				iExcelUploadService.loadTravelSchedulesUsingExcelAndUpdateTravelNetwork(workbook);
				return true;
		}
}
