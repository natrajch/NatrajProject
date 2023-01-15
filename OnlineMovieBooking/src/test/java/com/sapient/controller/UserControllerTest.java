package com.sapient.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import com.sapient.exeption.ApplicationException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

	@Test
	public void registerUser() {
		//verify if such user doesn't exist by getUser api 
		//call registerUser api
		////verify if such user exist by getUser api 
	}
	
	@Test
	public void getUserDetails() {
		//get User details of valid userId
	}
	
	@Test
	public void getUserDetailsForUserWhichDontExist() {
		//get User details of userId not in database
		//outcome : empty response
	}
	
	@Test
	public void fetchCommentReviewForMovie() {
		//check there exist comment for that movie by this user  (fetchReview api)
	}
	
	@Test
	public void fetchCommentReviewForMovieWithInvalidInput() {
		//supply invalid input such as empty or rating 0% (fetchReview api)
		//outcome : throw ApplicationException stating bad request
		assertThrows(ApplicationException.class, null);
	}
	
	@Test
	public void fetchCommentReviewForMovieNotInDB() {
		//supply input that dont exist in database (fetchReview api)
		//outcome : empty response
	}
	
	@Test
	public void postCommentReviewForMovie() {
		//check there is no comment for that movie by this user (fetchReview api)
		//invoke postReview api 
		//check there exist comment for that movie by this user  (fetchReview api)
	}
	
	@Test
	public void postCommentReviewWithInvalidInputForMovie() {
		//check there is no comment for that movie by this user (fetchReview api)
		//invoke postReview api by supplying either empty or special characters or abusive text or rating as 0%
		//outcome : throw ApplicationException stating bad request
		assertThrows(ApplicationException.class, null);
	}
	
	
	@Test
	public void postCommentReviewAgainForMovie() {
		//check there is comment for that movie by this user  (fetchReview api)
		//invoke postReview api 
		//outcome : throw ApplicationException ("Comment already exist for this movie. Please use edit option")
		assertThrows(ApplicationException.class, null);
	}
	
	
	@Test
	public void editCommentReviewForMovie() {
		//fetch comment for that movie by this user (fetchReview api)
		//invoke editReview api  
		//compare old/new exist comment for that movie by this user
	}
	
	
	@Test
	public void editCommentWithInvalidInputForMovie() {
		//fetch comment for that movie by this user  (fetchReview api)
		//invoke editReview api with empty input or special chars or abusive text only or rating as 0%
		//outcome : throw ApplicationException stating bad request
		assertThrows(ApplicationException.class, null);
	}
	
	@Test
	public void deleteCommentForMovie() {
		//fetch comment for that movie by this user (fetchReview api)
		//invoke deleteReview api 
		//verify it should return empty response for fetch for that movie by this user (fetchReview api)
	}
	
	@Test
	public void deleteCommentForMovieInvalidInput() {
		//invoke deleteReview api 
		//outcome : throw ApplicationException stating bad request
		assertThrows(ApplicationException.class, null);
	}
	
	
}
