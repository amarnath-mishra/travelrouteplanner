package com.amarnathtravels.routeplanner.model.flight;

import com.amarnathtravels.routeplanner.model.BookingStatus;
import com.amarnathtravels.routeplanner.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FlightSeat extends Seat {
		private FlightSeatClass seatClass;
		private String seatNumber;
		private int fare;
		private BookingStatus bookingStatus;

		public FlightSeat(FlightSeatClass seatClass, String seatNumber, BookingStatus bookingStatus) {
				this.seatClass = seatClass;
				this.seatNumber = seatNumber;
				this.bookingStatus = bookingStatus;
		}
}
