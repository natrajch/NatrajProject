package com.sapient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

	
	private String comment;
	private String rating;
	private String movieName;
	private String movieLanguage;
	private Long userId;
}
