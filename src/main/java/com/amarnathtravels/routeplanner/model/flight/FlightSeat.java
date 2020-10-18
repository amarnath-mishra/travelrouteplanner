package com.amarnathtravels.routeplanner.model.flight;

import com.amarnathtravels.routeplanner.model.BookingStatus;
import com.amarnathtravels.routeplanner.model.Seat;
import lombok.Data;

@Data
public class FlightSeat extends Seat {
		private FlightSeatClass seatClass;
		private int fare;
		private BookingStatus bookingStatus;
}
