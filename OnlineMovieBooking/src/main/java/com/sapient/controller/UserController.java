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

import com.sapient.dto.Review;
import com.sapient.dto.User;
import com.sapient.jpa.entity.UserEntity;

@RestController
public class UserController {
	
	
	//can be used either through JWT token based authentication using spring security or API gateway or 
	@GetMapping(name="/user/login")
	public ResponseEntity<UserEntity> login(){
		
		return new ResponseEntity<UserEntity>(new UserEntity(), HttpStatus.OK);
	}
	
	
	@GetMapping(name="/user/movie/review")
	public ResponseEntity<Review> fetchReview(@RequestParam Long userId,  @RequestParam String movieName, @RequestParam String movieLanguage){
		
		return new ResponseEntity<Review>(new Review(), HttpStatus.OK);
	}
	
	@PostMapping(name="/user/movie/review")
	public ResponseEntity<Boolean> postReview(@RequestBody Review review ){
		
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping(name="/user/movie/review")
	public ResponseEntity<Boolean> deleteReview(@RequestBody Review review ){
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@PostMapping(name="/user/movie/review")
	public ResponseEntity<Boolean> editReview(@RequestBody Review review ){
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	
	@PutMapping(name = "/user/register")
	public ResponseEntity<Boolean> registerUser(@RequestBody User user){
		
		//password should be stored in encrypted form in database
		return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}
	
	
	@GetMapping(name="/user/detail")
	public ResponseEntity<UserEntity> getUser(@RequestParam Long userId){
		
		return new ResponseEntity<UserEntity>(new UserEntity(), HttpStatus.OK);
	}
	
}
