package com.sapient.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TheatreControllerTest {

		@Test
		public void getTheatreDetails() {
			//get theatre details
		}
		
		@Test
		public void getTheatreDetailsForInputNotInDB(){
			
		}
		
		
		@Test
		public void getTheatreDetailsForInvalidInput(){
			
		}
		
		@Test
		public void addTheatre() {
			//query getTheatreDetailsByNameAndCity api to see its returning empty
			//add theatre 
			////query getTheatreDetailsByNameAndCity api to see its returning newly added theatre
		}
		
		
		@Test
		public void addTheatreWhichIsAlreadyInDb() {
			
			//query getTheatreDetailsByNameAndCity api to see its returning non-empty
			//add theatre 
			//throw ApplicationException as "Theatre already present"
		}
		
		@Test
		public void addTheatreInvalidInput() {
			
			//supply empty all or partial input params
			//add theatre 
			//throw ApplicationException stating bad request
		}
		
}
