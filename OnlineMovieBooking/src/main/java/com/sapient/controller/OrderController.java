package com.sapient.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.dto.BookingRequest;
import com.sapient.dto.Order;
import com.sapient.dto.OrderParams;
import com.sapient.dto.Ticket;
import com.sapient.service.OrderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class OrderController {
	
	/*
	 * 
	 * Seat booking will be a two step process 
	 * step 1. Initiate booking by redirecting the user to third party payment unique url
	 * step 2. Once payment is done at third party payment gateway, it will be assumed that it is redirected back to application with details on url params.
	 *         These params are then sent to API to mark booking as Complete and then ticket item will be created in database.
	 * step 3. Period Thread (every 5 minutes) will be executed to check for INITIATED_ORDER_BOOKING_STATUS in the Order table and increment the count (WAIT_COUNT in OrderEntity) 
	 *         If the count in the thread crossed the threshold (count changed to 3 which means 15 mins crossed). Unblock such seats
	 */
	
	
	@Autowired
	OrderService orderService;
	
	
	@ApiOperation(value = "This method is responsible to initiate booking by redirecting the user to third party payment unique url. This will also block the seats.", httpMethod = "POST", response = String.class)
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Exception occurred while execution"), @ApiResponse(code = 400, message = "UnAuthorized"),
			 			   @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 200, message = "Initiated Successfully")})
	@PostMapping(name="/order/initiatebooking")
	public ResponseEntity<String> initiateBooking(@RequestBody BookingRequest bookingRequest){
		
		return new ResponseEntity<String>(orderService.startBooking(bookingRequest), HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "This method is responsible to resume booking by capturing information after redirected back to application from payment gateway with details on url params.\r\n"+
			              "These params will decide to mark booking as Complete and then ticket will be issued as the outcome on successful payment verification.", httpMethod = "POST", response = Ticket.class)
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Exception occurred while execution"), @ApiResponse(code = 400, message = "UnAuthorized"),
			 			   @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 200, message = "COmpleted Successfully")})
	@PostMapping(name="/order/completebooking")
	public  ResponseEntity<Ticket> completeBooking(@RequestBody OrderParams orderParams){
		
		return new ResponseEntity<Ticket>(orderService.completeBooking(orderParams), HttpStatus.CREATED);
	}
	

	@ApiOperation(value = "This method is responsible to resume booking by capturing information after redirected back to application from payment gateway with details on url params.\r\n"+
            "These params will decide to mark booking as Complete and then ticket item will be issued.", httpMethod = "POST", response = Ticket.class)
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Exception occurred while execution"), @ApiResponse(code = 400, message = "UnAuthorized"),
			   			   @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 200, message = "Data fetched successfully")})
	@GetMapping(name="/orders/history")
	public ResponseEntity<List<Order>> getOrdersHistory(@RequestParam String userId){
		
		return new ResponseEntity<List<Order>>(new ArrayList<Order>(), HttpStatus.OK);
	}
}
