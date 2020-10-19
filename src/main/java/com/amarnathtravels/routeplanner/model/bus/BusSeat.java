package com.amarnathtravels.routeplanner.model.bus;

import com.amarnathtravels.routeplanner.model.BookingStatus;
import com.amarnathtravels.routeplanner.model.Seat;
import com.amarnathtravels.routeplanner.model.flight.FlightSeatClass;
import lombok.Data;

@Data
public class BusSeat extends Seat {
		private BusSeatClass seatClass;
		private String seatNumber;
		private int fare;
		private BookingStatus bookingStatus;

		public BusSeat(BusSeatClass seatClass, String seatNumber, BookingStatus bookingStatus) {
				this.seatClass = seatClass;
				this.seatNumber = seatNumber;
				this.bookingStatus = bookingStatus;
		}
}
