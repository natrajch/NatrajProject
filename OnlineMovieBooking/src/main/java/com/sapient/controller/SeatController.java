package com.sapient.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.dto.ShowDetails;
import com.sapient.dto.TheatreShows;

@RestController
public class SeatController {
	
	
	@PutMapping("/theatre/seats/add")
	public ResponseEntity<Boolean> addTheatreSeats(@RequestBody TheatreShows theatreShows){
		
		return  new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/theatre/seats/remove")
	public ResponseEntity<Boolean> removeTheatreSeats(@RequestBody TheatreShows theatreShows){
		
		//before removing seats, we need to make sure there any new shows that are booked for today or future		
		return  new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@PostMapping("/theatre/seats/update")
	public ResponseEntity<Boolean> updateTheatreSeats(@RequestBody TheatreShows theatreShows){
		
		return  new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	
	//client can use below get() to get build request for Create/Update/delete transactions
	@GetMapping("/theatre/seats")
	public ResponseEntity<TheatreShows> getTheatreSeats(@RequestParam String theatreName,  @RequestParam String theatreCity){
		
		return  new ResponseEntity<TheatreShows>(new TheatreShows(), HttpStatus.OK);
	}
	

}
