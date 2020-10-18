package com.amarnathtravels.routeplanner.controller;

import com.amarnathtravels.routeplanner.model.flight.FlightSchedule;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExcelUploadController {
		@PostMapping("/uploadFlights")
		@ResponseBody
		public Boolean uploadFlightsToDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws
				IOException {
				XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());

				return true;
		}
}
