package com.amarnathtravels.routeplanner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

		@ExceptionHandler(value = DestNotReachableException.class)
		public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(DestNotReachableException e) {
				CustomErrorResponse error = new CustomErrorResponse("EmptyResponse", e.getMessage());
				error.setTimestamp(LocalDateTime.now());
				error.setStatus((HttpStatus.OK.value()));
				return new ResponseEntity<>(error, HttpStatus.OK);
		}
		@ExceptionHandler(value = Exception.class)
		public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(Exception e) {
				CustomErrorResponse error = new CustomErrorResponse("Error ", "SOME_ERROR_OCCURRED, will get fixed asap");
				error.setTimestamp(LocalDateTime.now());
				error.setStatus((HttpStatus.INTERNAL_SERVER_ERROR.value()));
				return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
}
