package com.sapient.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import com.sapient.exeption.ApplicationException;
import com.sapient.exeption.NotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderControllerTest {
	
	@Test
	public void startOrderBooking() {
		//send booking request with all the seats/show/movie/theatre info
		//expected outcome : get the redirect url
	}
	
	@Test
	public void startOrderBookingForBookedSeats() {
		//send booking request with all the seats/show/movie/theatre but for one or more of seats which are already booked
		//expected outcome : throw ApplicationException (Seats Already Reserved)
	}
	
	@Test
	public void startOrderBookingForDuplicateSeatsWithinRequest() {
		//send booking request with all the seats/show/movie/theatre but one or more seats have same seat numbers in the request received
		//expected outcome : throw ApplicationException (Duplicate Seats in the request)
		  assertThrows(ApplicationException.class, null);
	}

	@Test
	public void startOrderBookingForSameSeatsConcurrentRequests() {
		//scenario is rare for concurrent booking of same seats at same time by two or more users initiate booking
		//send booking request  with all the seats/show/movie/theatre info by user1
		//expected outcome : throw ApplicationException (seats are already booked) for one of the user  
	    assertThrows(ApplicationException.class, null);
	}

	
	@Test
	public void completeOrderBookingSucess() {
		//payment successful at third party payment gateway
		//expected outcome : verify ticket is created 
	}
	
	
	@Test
	public void completeOrderBookingFailed() {
		//payment not successful at third party payment gateway (such as insufficient balance)
		//expected outcome : throws ApplicationException ("Order not successful with appropriate order status") 
	    assertThrows(ApplicationException.class, null);
	}
	
	
	@Test
	public void completeOrderBookingCancelled() {
		//payment not successful at third party payment gateway (such as user clicked on Cancel button at third party payment gateway)
		//expected outcome : throws ApplicationException ("Order not successful with appropriate order status") 
	    assertThrows(ApplicationException.class, null);
	}
	
	
	@Test
	public void completeOrderNeverInvoked() {
		//this requires Manual testing where user initiated booking but never got redirected back to application for completing the transaction (completeBooking api not called)
		//Tester should check that seats which were blocked should be unblocked after some defined configuration time (15 mins)
	}
	
	
	@Test
	public void getOrderHistory() {
		//supply registered user id 
		//expected outcome : should get user's orders
	}
	
	@Test
	public void getOrderHistoryInvalidUserId() {
		//supply invalid user id 
		//expected outcome : throw NotFoundException ("Invalid User")
	    assertThrows(NotFoundException.class, null);
	}
	
	
}
