package com.amarnathtravels.routeplanner.exception;

public class DestNotReachableException extends Exception{
		private String message;
		@Override public String getMessage() {
				return message;
		}
		public DestNotReachableException(String message) {
				super(message);
				this.message = message;
		}
}
