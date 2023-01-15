package com.sapient.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import com.sapient.exeption.ApplicationException;
import com.sapient.exeption.NotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketControllerTest {
	
	@Test
	public void getTicket(){
		//supply valid ticket id and get ticket details
	}

	@Test
    public void getTicketWithEmptyTicketId(){
		//supply ticket id as null
    	//throw ApplicationException as bad request
    	  assertThrows(ApplicationException.class, null);
	}

	@Test
    public void getTicketWithTicketIdNotInDB(){
  		//supply valid ticket id which don't exist in database
      	//outcome : empty
  	}
    
	@Test
    public void cancelTicket() {
    	//supply ticket id to cancel
    	//outcome : should get cancel reference id 
    }
	
	@Test
    public void cancelTicketForCompletedShow() {
    	//booked seats has showdate or time has passed over
		//supply ticket id to cancel
    	//outcome : throw ApplicationException ("Ticket cancellation is not allowed")
		 assertThrows(ApplicationException.class, null);
    }
    
	@Test
    public void cancelTicketforTicketIdNotInDb() {
    	//supply  ticket id which dont exist in database
      	//throw NotFoundException (Ticket do not exist)
      	  assertThrows(NotFoundException.class, null);
    }
	
	
	@Test
    public void cancelTicketInvalidInput() {
    	//supply  ticket id as empty
      	//throw ApplicationException stating bad request
      	  assertThrows(ApplicationException.class, null);
    }
}
