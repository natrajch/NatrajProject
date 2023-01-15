package com.sapient.dto;

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
@ApiModel(description = "User object")
public class User {
	
	@Size(min = 10, max = 10)
	private long mobileNumer;
	@NotNull
	private String emailId;
	private String password; //will always be shared in encrypted on a demand basis
	private String fullName;

}
