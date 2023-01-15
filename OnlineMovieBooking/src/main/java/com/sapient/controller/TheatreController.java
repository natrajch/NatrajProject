package com.sapient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.dto.TheatreDto;
import com.sapient.dto.TheatreShows;
import com.sapient.service.TheatreService;

@RestController
public class TheatreController {
	
	
	@Autowired
	TheatreService theatreService;
	
	@GetMapping(name = "/theatre/details")
	public ResponseEntity<TheatreDto> getTheatreDetailsByNameAndCity(@RequestParam(name = "theatre_name", required = true) String name, 
			@RequestParam(name = "city", required = true) String city) {
		
		return new ResponseEntity<TheatreDto>(theatreService.getTheatreDetails(name,city), HttpStatus.OK);
	}
	
	
	
	@PutMapping(name = "/theatre/add")
	public boolean addTheatre(@RequestBody TheatreDto theatreDetails) {
		//indicate success or fail with appropriate HTTP Status 
		//also share the the link to get theatre details using HATEOAS
		return true;
	}

	@DeleteMapping(name = "/theatre/remove")
	public boolean removeTheatre(@RequestBody TheatreDto theatreDetails) {
		return true;
	}
	
	@PostMapping(name = "/theatre/modify")
	public boolean updateTheatre(@RequestBody TheatreDto theatreDetails) {
		return true;
	}
	
}
