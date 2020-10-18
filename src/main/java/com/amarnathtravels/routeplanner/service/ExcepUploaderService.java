package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.dao.IAirportDao;
import com.amarnathtravels.routeplanner.dao.IFlightDao;
import com.amarnathtravels.routeplanner.model.BookingStatus;
import com.amarnathtravels.routeplanner.model.flight.Flight;
import com.amarnathtravels.routeplanner.model.flight.FlightSeat;
import com.amarnathtravels.routeplanner.model.flight.FlightSeatClass;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
public class ExcepUploaderService implements IExcelUploadService{
		private final IFlightDao flightDao;
		private final IAirportDao airportDao;
		@Override
		public boolean loadAirportsUsingExcel(XSSFWorkbook workbook) {
				return false;
		}

		@Override
		public boolean loadFlightsUsingExcel(XSSFWorkbook workbook) {
				try {
						Map<String, Flight> flightMap =  new HashMap<>();
						XSSFSheet worksheet = workbook.getSheetAt(0);
						String dateTimePattern = "yyyy MMM dd HH:mm zzz";
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
						for(int i=0;i<worksheet.getPhysicalNumberOfRows() ;i++) {
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
				flight.setSource(airportDao.getAllAirportsMap().get(row.getCell(1).getStringCellValue()));
				flight.setDest(airportDao.getAllAirportsMap().get(row.getCell(2).getStringCellValue()));
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
		public boolean loadGraphUsingExcel(XSSFWorkbook workbook) {
				return false;
		}
}
