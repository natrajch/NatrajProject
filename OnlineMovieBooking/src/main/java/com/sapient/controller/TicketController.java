package com.sapient.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.dto.BulkTickets;
import com.sapient.dto.ShowDetails;
import com.sapient.dto.Ticket;
import com.sapient.service.TicketService;

@RestController
public class TicketController {
	
	
	@Autowired
	TicketService ticketService;
	
	@GetMapping( name = "/movie/ticket")
	public ResponseEntity<Ticket> getTicketDetails(@RequestParam (required = true)  @NotNull long ticketId){
	
		return new ResponseEntity<Ticket>(new Ticket(), HttpStatus.OK); 
	}

	@PostMapping(name = "/movie/tickets/cancel")
	public ResponseEntity<Map<Long, Long>> cancelBulkTickets(@RequestParam BulkTickets bulkTickets){

		return new ResponseEntity<Map<Long, Long>>(ticketService.cancelBulkTickets(bulkTickets), HttpStatus.OK); 
	}
	
	
	@PostMapping(name = "/movie/ticket/cancel")
	public ResponseEntity<Long> cancelTicket(@RequestParam (required = true) @NotNull long ticketId){

		return new ResponseEntity<Long>(ticketService.cancelTicket(ticketId), HttpStatus.OK); 
	}
	
	@PostMapping(name = "/movie/show/alltickets/cancel")
	public ResponseEntity<Boolean> cancelShowTickets(@RequestParam ShowDetails showDetails){

		return new ResponseEntity<Boolean>(ticketService.cancelShowTickets(showDetails), HttpStatus.OK); 
	}
	
	@PostMapping(name = "/movie/shows/alltickets/cancel")
	public ResponseEntity<Boolean> cancelMultipleShowsTickets(@RequestParam List<ShowDetails> showDetailsList){

		return new ResponseEntity<Boolean>(ticketService.cancelMultipleShowsTickets(showDetailsList), HttpStatus.OK); 
	}
	
	
}
