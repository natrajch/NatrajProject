package com.sapient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
	
	private Long ticketId;
	private String showTime;
	private String showDate;
	private String movieName;
	private String seatNumbers;
	private String theatreName;
	

}
