package com.sapient.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheatreShows {

	
	private String theatreName;
	private String theatreCity;
	private List<Show> shows; 
	
}
