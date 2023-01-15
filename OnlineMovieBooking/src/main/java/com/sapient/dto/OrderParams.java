package com.sapient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderParams {
	
	private String thirdPartyRefId;
	private String status;
	private long orderId;

}
