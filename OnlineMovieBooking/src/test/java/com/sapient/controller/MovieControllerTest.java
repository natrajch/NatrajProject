package com.sapient.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import com.sapient.exeption.ApplicationException;
import com.sapient.exeption.NotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieControllerTest {

	
	@Test
	public void getMovieDetails() {
		//supply movie name and language
		//outcome : movie details are returned
	}
	
	@Test
	public void getMovieDetailsInvalidInput() {
		//supply invalid movie name (blank or special characters) and(or) language (other than enumerated strings for lang)
		//outcome : throw ApplicationException (Invalid input) as bad request
		assertThrows(ApplicationException.class, null);
	}
	
	@Test
	public void getMovieDetailsNotInDb() {
		//supply  movie name and(or) language which dont exist in database
		//outcome : empty
		//we need not throw exception for GET requests
	}
	
	@Test
	public void  getAllMoviesRunningInTheCity(){
		//gets all the movies that are listed in the city
	}
	
	
	@Test
	public void  getAllMoviesRunningInTheCityWithInvalidCity(){
		//supply invalid city name as number or empty
		//outcome : throw ApplicationException (Invalid input) as bad request
		assertThrows(ApplicationException.class, null);
	}
	
	@Test
	public void  getAllMoviesRunningInTheCityWithCityNotInDb(){
		//supply city that don't exist in database
		//outcome : empty
	}
	
	@Test
	public void getMovieReview(){
		//gets all the reviews and rating of that movie
	}
	
	@Test
	public void addMovie() {
		//supply movieDetail object
		//verify by calling getMovieDetail api to verify add operation
	}
	
	@Test
	public void addMovieWithMissingInputs() {
		//supply movieDetail object but partial inputs
		//outcome : throw ApplicationException (Invalid input) as bad request
		assertThrows(NotFoundException.class, null);
	}
	
	@Test
	public void removeMovie() {
		//verify by calling getAllMovies api to check movie is present
		//supply inputs
		// removeMovie api is called
		//verify by calling getAllMovies api to check movie is not present
	}
	
	@Test
	public void removeMovieWithMissingInputs() {
		//supply movieDetail object but partial inputs
		//outcome : throw ApplicationException (Invalid input) as bad request
		assertThrows(NotFoundException.class, null);
	}
	
	
	
}
