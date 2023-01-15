package com.sapient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreShowsRequest {

	
	private String movieName;
	private String movieLanguage;
	private String showDate;
	private String showTime;
	private String city;
}
