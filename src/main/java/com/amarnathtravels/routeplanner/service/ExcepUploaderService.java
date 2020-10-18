package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.dao.IAirportDao;
import com.amarnathtravels.routeplanner.dao.IFlightDao;
import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.model.BookingStatus;
import com.amarnathtravels.routeplanner.model.flight.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class ExcepUploaderService implements IExcelUploadService{
		private final IFlightDao flightDao;
		private final IAirportDao airportDao;
		@Override
		public boolean loadAirportsUsingExcel(XSSFWorkbook workbook) {
				try {
						Map<String, Airport> airportMap =  new HashMap<>();
						XSSFSheet worksheet = workbook.getSheetAt(0);
						for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
								XSSFRow row = worksheet.getRow(i);
								Airport airport = createAirport(row);
								airportMap.putIfAbsent(airport.getCode(),airport);
						}
						return airportDao.saveAllAirports(airportMap);

				} catch (Exception e){
						System.out.println("Exception occurred while loading flights");
				}
				return false;
		}

		private Airport createAirport(XSSFRow row) {
			Airport airport = new Airport();
			airport.setName(row.getCell(0).getStringCellValue());
			airport.setAddress(row.getCell(1).getStringCellValue());
			airport.setCode(row.getCell(2).getStringCellValue());
			airport.setAirportType(AirportType.valueOf(row.getCell(3).getStringCellValue()));
			airport.setCountry(Country.valueOf(row.getCell(4).getStringCellValue()));
			return airport;
		}

		@Override
		public boolean loadFlightsUsingExcel(XSSFWorkbook workbook) {
				try {
						Map<String, Flight> flightMap =  new HashMap<>();
						XSSFSheet worksheet = workbook.getSheetAt(0);
						String dateTimePattern = "yyyy MMM dd HH:mm zzz";
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
						for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
								XSSFRow row = worksheet.getRow(i);
								Flight flight = createFlight(row);
								flightMap.putIfAbsent(flight.getFlightNo(),flight);
						}
						return flightDao.saveAllFlights(flightMap);

				} catch (Exception e){
						System.out.println("Exception occurred while loading flights");
				}
				return false;
		}

		private Flight createFlight(XSSFRow row) {
				Flight flight = new Flight();
				flight.setFlightNo(row.getCell(0).getStringCellValue());
				flight.setSource(airportDao.findAirportByCode(row.getCell(1).getStringCellValue()));
				flight.setDest(airportDao.findAirportByCode(row.getCell(2).getStringCellValue()));
				int numberOfBusinessSeats = (int) row.getCell(3).getNumericCellValue();
				int numberOfEconomySeats = (int) row.getCell(4).getNumericCellValue();
				//				int businessPrice = (int) row.getCell(5).getNumericCellValue();
				//				int economyPrice = (int) row.getCell(6).getNumericCellValue();
				//				CurrencyType currency = CurrencyType.valueOf(row.getCell(7).getStringCellValue());
				List<FlightSeat> flightSeats = createSeats(numberOfBusinessSeats, numberOfEconomySeats);
				flight.setSeats(flightSeats);
				return flight;
		}

		private List<FlightSeat> createSeats(int numberOfBusinessSeats, int numberOfEconomySeats) {
				List<FlightSeat> flightSeats = new ArrayList<>();
				for(int i=0;i<numberOfBusinessSeats;i++){
						flightSeats.add(new FlightSeat(FlightSeatClass.BUSINESS,"b"+i, BookingStatus.WAITING));
				}
				for(int i=0;i<numberOfEconomySeats;i++){
						flightSeats.add(new FlightSeat(FlightSeatClass.ECONOMY,"e"+i, BookingStatus.WAITING));
				}
				return flightSeats;
		}

		@Override
		public boolean loadFlightSchedulesUsingExcelAndCreateTravelNetwork(XSSFWorkbook workbook) {
				try {
						Map<String, Flight> flightMap =  new HashMap<>();
						XSSFSheet worksheet = workbook.getSheetAt(0);
						String dateTimePattern = "yyyy MMM dd HH:mm zzz";
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
						for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
								XSSFRow row = worksheet.getRow(i);
								Connection connection = createConnection(row);
								flightMap.putIfAbsent(flight.getFlightNo(),flight);
						}
						return flightDao.saveAllFlights(flightMap);

				} catch (Exception e){
						System.out.println("Exception occurred while loading flights");
				}
				return false;
				return false;
		}

		private Connection createConnection(XSSFRow row) {
				Connection connection = new Connection();
				String dateTimePattern = "yyyy MMM dd HH:mm zzz";
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
				connection.setFlight(flightDao.findFlightByCode(row.getCell(0).getStringCellValue()));
				connection.setArrivalTime(LocalDateTime.parse(row.getCell(0).getStringCellValue(), formatter));
				connection.setDeptTime(LocalDateTime.parse(row.getCell(0).getStringCellValue(), formatter));
				airportDao.findAirportByCode(connection.getFlight().getSource().getCode()).set
		}
}
