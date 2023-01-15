package com.sapient.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.dto.MovieDetail;
import com.sapient.dto.MovieReview;

@RestController
public class MovieController {

	
	@PutMapping("/movie/add")
	public ResponseEntity<Boolean> addMovie(@RequestParam String movieName, @RequestParam String movieLanguage){
		
		return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/movie/remove")
	public ResponseEntity<Boolean> removeMovie(@RequestParam String movieName, @RequestParam String movieLanguage, @RequestParam String city){
		
		//just update is_active as N
		//note: movie removal should check for any active tickets (today or future dated) by connecting theatre(city)->shows->movies
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@GetMapping("/movie/review")
	public ResponseEntity<MovieReview> getMovieReview(@RequestParam String movieName, @RequestParam String movieLanguage){
		
		return new ResponseEntity<MovieReview>(new MovieReview(), HttpStatus.OK);
	}
	
	
	@GetMapping("/movie/details")
	public ResponseEntity<MovieDetail> getMovieDetails(@RequestParam String movieName, @RequestParam String movieLanguage){
		
		return new ResponseEntity<MovieDetail>(new MovieDetail(), HttpStatus.OK);
	}
	
	@GetMapping("/movies/all")
	public ResponseEntity<List<MovieDetail>> getAllMovies(@RequestParam (required = true) String city){
		
		//this should be retrieved by connecting theatre(city)->shows->movies
		return new ResponseEntity<List<MovieDetail>>(new ArrayList<MovieDetail>(), HttpStatus.OK);
	}
	
}
