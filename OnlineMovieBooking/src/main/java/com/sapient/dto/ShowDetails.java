package com.sapient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowDetails {

	private String showDate;
	private String showTime;
	private String theatreName;
	private String theatreCity;
	private String movieName;
	private String movieLanguage;
}
