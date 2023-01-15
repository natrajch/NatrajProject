package com.sapient.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheatreDto implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String theatreName;
	private String theatreCity;
	private String theatreAddress;
	private String theatreCountry;
}
