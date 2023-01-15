package com.sapient.dto;
import org.junit.jupiter.api.Test;

public class DTOTest {

	
	@Test
	public void testDTOClasses() {
		PojoTestUtils.validatePojoClasses(BookingRequest.class, true);
		//same follows for all other DTO classes
	}
}
