package com.sapient.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDetail {

	
	private String movieName;
	private String movieLanguage;
	private String grade;
	private String castCrew;
	private String rating; //will be taken average of all users who rated this movie/language
}
