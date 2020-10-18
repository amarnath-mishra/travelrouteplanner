package com.amarnathtravels.routeplanner.controller;

import com.amarnathtravels.routeplanner.model.flight.FlightSchedule;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Controller
public class LoadFlightsController {

		@GetMapping("/hello1")
		@ResponseBody
		public String hello(){
				return "hello";
		}
//		@PostMapping("/import")
//		@ResponseBody
//		public List<FlightSchedule> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
//
//				List<FlightSchedule> flightList = new ArrayList<FlightSchedule>();
//				XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
//
//		}
}
