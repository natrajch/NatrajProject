package com.sapient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

	private long seatInternalId;
	private String seatNumber;
	private String seatCategory;
	private boolean isBooked;
}
