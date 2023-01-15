package com.sapient.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "user selected seats which are to be booked")
public class BookingRequest {
	
  @NotNull
  @Size(max = 4)
  private String showTime; 
  @NotNull
  private String showDate;
  @NotNull
  private String movieName;
  @NotNull
  private String movieLanguage;
  @NotNull
  @Size(min = 1, max = 10)
  private List<String> seatNumbers;
  @NotNull
  @Size(min = 1, max = 10)
  private Long userId;
  @NotNull
  private String theatreName;
  @NotNull
  private String theatreCity;
  
}
