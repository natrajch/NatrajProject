package com.sapient.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import com.sapient.exeption.ApplicationException;
import com.sapient.exeption.NotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeatControllerTest {

    
	@Test
    public void listTheatreSeatsForGivenTheatreByTheatreNameCity(){
        //verify if seats are retreived for the given theatre name and city
    }
    
	@Test
    public void listTheatreSeatsForTheatreWhichDontExistInDb(){
       //outcome : empty response 
    }
	
	@Test
    public void listTheatreSeatsForInvalidInput(){
        //such as empty input or input that contain invalid characters or partial input
		//outcome : throw ApplicationException as bad request
    }
	
	
	@Test
	public void addTheatreSeats() {
		//query getTheatreSeats api to get count
		//add seats to the given theatre
		//query getTheatreSeats api to verify newly added seats and compare count before/after
	}
	
	@Test
	public void addTheatreSeatsWithSameSeatNumbersWithinRequest() {
	
		//should throw exception if we supplying with duplicate seat numbers in the request
		 assertThrows(ApplicationException.class, null);
	}
	
	
	@Test
	public void addTheatreSeatsAgainWithSameSeatNumbersPresentInDb() {
	
		//should throw exception if we supplying with same seat numbers which are already in database
		 assertThrows(ApplicationException.class, null);
	}
	
	
	@Test
	public void addTheatreSeatsCrossingCapacity() {
	
		//should throw exception if we supplying seats that cross the theatre capacity
		 assertThrows(ApplicationException.class, null);
	}
    
	@Test
	public void removeTheatreSeats() {
		//query getTheatreSeats api to get count
		//remove seats from the given theatre
		//query getTheatreSeats api to verify seats are removed and compare count before/after
	}
	
	@Test
	public void removeTheatreSeatsWhichAreBookedForCurrentFuture() {
		//if we supplying seats which are booked for today or future dated
		//should throw exception as "There are seats booked for this theatre in Current and Future Dated" 
		 assertThrows(ApplicationException.class, null);
	}
}
