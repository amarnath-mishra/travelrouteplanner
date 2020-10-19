package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.dao.IAirportDao;
import com.amarnathtravels.routeplanner.dao.IFlightDao;
import com.amarnathtravels.routeplanner.dao.IGraphDao;
import com.amarnathtravels.routeplanner.dao.route.BusConnection;
import com.amarnathtravels.routeplanner.dao.route.Connection;
import com.amarnathtravels.routeplanner.dao.route.FlightConnection;
import com.amarnathtravels.routeplanner.dao.route.TravelMode;
import com.amarnathtravels.routeplanner.model.BookingStatus;
import com.amarnathtravels.routeplanner.model.bus.BusSeat;
import com.amarnathtravels.routeplanner.model.bus.BusSeatClass;
import com.amarnathtravels.routeplanner.model.flight.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExcelUploaderService implements IExcelUploadService{
		private final IFlightDao flightDao;
		private final IAirportDao airportDao;
		private final IBusDao busDao;
		private final IBusStandDao busStandDao;
		private final IGraphDao iGraphDao;
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
//				flight.setSource(airportDao.findAirportByCode(row.getCell(1).getStringCellValue()));
//				flight.setDest(airportDao.findAirportByCode(row.getCell(2).getStringCellValue()));
				int numberOfBusinessSeats = (int) row.getCell(1).getNumericCellValue();
				int numberOfEconomySeats = (int) row.getCell(2).getNumericCellValue();
				//CurrencyType currency = CurrencyType.valueOf(row.getCell(7).getStringCellValue());
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
						XSSFSheet worksheet = workbook.getSheetAt(0);
						Map<String, Connection> connectionMap =  new HashMap<>();
						FlightConnection fc = (FlightConnection) connectionMap.get("sjd");
						for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
								XSSFRow row = worksheet.getRow(i);
								Connection connection = createConnection(row);
								connectionMap.putIfAbsent(connection.getFlight().getFlightNo(),connection);
						}
						return flightDao.saveAllFlights(flightMap);

				} catch (Exception e){
						System.out.println("Exception occurred while loading flights");
				}
				return false;
				return false;
		}

		private Connection createConnection(XSSFRow row) {

				TravelMode travelMode = TravelMode.valueOf(row.getCell(0).getStringCellValue());
				//TODO:Can Use Factory Design Pattern for this
				if(travelMode==TravelMode.FLIGHT){
						return createFlightConnection(row);
				} else {
						return createBusConnection(row);

				}



				//airportDao.findAirportByCode(connection.getFlight().getSource().getCode()).set
		}

		private Connection createBusConnection(XSSFRow row) {
				BusConnection connection = new BusConnection();
				connection.setTravelMode(TravelMode.BUS);
				String dateTimePattern = "yyyy MMM dd HH:mm zzz";
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
				connection.setBus(busDao.findBusByCode(row.getCell(0).getStringCellValue()));
				connection.setSource(busStandDao.findBusStandByCode(row.getCell(1).getStringCellValue()));
				connection.setDest(busStandDao.findBusStandByCode(row.getCell(2).getStringCellValue()));
				connection.setDeptTime(LocalDateTime.parse(row.getCell(3).getStringCellValue(), formatter));
				connection.setArrivalTime(LocalDateTime.parse(row.getCell(4).getStringCellValue(), formatter));
				connection.setStatus(Status.valueOf(row.getCell(5).getStringCellValue()));
				List<BusSeat> seats = busDao.findBusByCode(connection.getBus().getBusNo()).getSeats();
				connection.setConnId(connection.getBus().getBusNo() + connection.getSource().getCode()+ (new Date().getTime()));
				int sleeperPrice = (int) row.getCell(6).getNumericCellValue();
				int sittingPrice = (int) row.getCell(7).getNumericCellValue();

				//TODO:: Do it for Bus
				seats.forEach(busSeat -> {
						if(busSeat.getSeatClass()== BusSeatClass.SLEEPER){
								busSeat.setFare(sleeperPrice);
						} else{
								busSeat.setFare(sittingPrice);
						}
				});
		}

		private FlightConnection createFlightConnection(XSSFRow row) {
				FlightConnection connection = new FlightConnection();
				String dateTimePattern = "yyyy MMM dd HH:mm zzz";
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
				connection.setTravelMode(TravelMode.FLIGHT);
				connection.setFlight(flightDao.findFlightByCode(row.getCell(0).getStringCellValue()));
				connection.setSource(airportDao.findAirportByCode(row.getCell(1).getStringCellValue()));
				connection.setDest(airportDao.findAirportByCode(row.getCell(2).getStringCellValue()));
				connection.setDeptTime(LocalDateTime.parse(row.getCell(3).getStringCellValue(), formatter));
				connection.setArrivalTime(LocalDateTime.parse(row.getCell(4).getStringCellValue(), formatter));
				connection.setStatus(Status.valueOf(row.getCell(5).getStringCellValue()));
				List<FlightSeat> seats = flightDao.findFlightByCode(connection.getFlight().getFlightNo()).getSeats();
				connection.setConnId(connection.getFlight().getFlightNo() + connection.getSource().getCode()+ (new Date().getTime()));
				int businessPrice = (int) row.getCell(6).getNumericCellValue();
				int economyPrice = (int) row.getCell(7).getNumericCellValue();
				seats.forEach(flightSeat -> {
						if(flightSeat.getSeatClass()==FlightSeatClass.BUSINESS){
								flightSeat.setFare(businessPrice);
						} else{
								flightSeat.setFare(economyPrice);
						}
				});
				return connection;
		}
}
