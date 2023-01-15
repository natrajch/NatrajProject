package com.sapient.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Show {

	private Long showId;
	private String showDate;
	private String showTime;
	private String movieName;
	private String movieLanguage;
	
	List<Seat> seats;

	
	public String toStringCustom() {
		return "Show [showId=" + showId + ", showDate=" + showDate + ", showTime=" + showTime + ", movieName="
				+ movieName + ", movieLanguage=" + movieLanguage + "]";
	}
	
	
	
	
}
