package com.sapient.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShowControllerTest {

	
	@Test
	void getTheatreShowsByTheatreNameAndCity() {
		//should list all the shows of the given theatre and the city in which theatre is present
	}
	
	@Test
	void getTheatreShowsByMovieSelectedWithShowDateAndCity() {
		//list all the theatres with all the shows that premiere the given movie for the specific date
	    //Outcome expected: should contain all the shows of all theatres for a given that premier this movie
	}
	
	@Test
    void getTheatreShowsByNameAndCityNotInDatabase(){
        //supply inputs which don't exist in database
	    //outcome expected: empty object
    }
	
	@Test
    void getTheatreShowsByNameAndCityWithIncorrectInput(){
        //supply invalid inputs or names which or partial input empty inputs
	    //outcome expected: throw ApplicationException stating bad request
    }
	
	
	@Test
	void addShowsToTheatre() {
	    //call addTheatreShows api
		//should be able to validate by calling test case getTheatreShowsByMovieSelectedWithShowDateAndCity() after adding  new shows to the theatre 
	}
	
	@Test
    void supplyDuplicateShowsToAddForTheatre() {
        //pass 2 show inputs with duplicate combination (showTime/ShowDate/Theatre)
	    //call addTheatreShows api
        //outcome : should throw error stating error as Same show criteria already exist
    }
	
	
	@Test
    void supplyDuplicateShowToAddWhichExistAlreadyInDbForSameTheatre() {
        //pass show inputs with same combination (showTime/ShowDate/Theatre) as present in database
        //call addTheatreShows api
        //outcome : should throw error stating error as Same show criteria already exist
    }
	

	@Test
	void removeShowsFromTheatre() {
	    //call removeTheatreShows api
	   //should be able to validate by calling test case getTheatreShowsByMovieSelectedWithShowDateAndCity() after removing  existing shows from the theatre 
	}
	
	   @Test
	 void removeBookedShowsFromTheatre() {
	    //call removeTheatreShows api
	    //should be able to validate by calling test case getTheatreShowsByMovieSelectedWithShowDateAndCity() after removing  existing shows from the theatre 
	    //outcome: all the tickets of the show are marked cancelled (can be verified by querying via showId from ShowSeatsBookingEntity->OrderEntity->TicketEntity to retrieve tickets for verification (will have cancelReferenceId attribute data populated)
	    //also verify notifyUsersViaSMS method is called one or more times (number decided by how many users booked tickets for this show)
	   }
	   
	
	@Test
	void updateExistingShowDateInTheatre() {
	    //assumption made no tickets are booked for the show we wanted to update
	    //query by calling getTheatreShowsByTheatreNameAndCity Rest api to get show Id and its show details for which we need to update show details 
		//call updateTheatreShows api by providing new show date for the show Id
	    //After above is done, verify this by steps defined in test case getTheatreShowsByTheatreNameAndCity to see updated showDate for the above show Id
	    //also verify notifyUsersViaSMS method is called zero times
	}
	
	@Test
    void bookTicketsAndThenPerformUpdateShowDateForExistingShow() {
        
        //query to get show Id and its show details for which we need to update show details by calling getTheatreShowsByTheatreNameAndCity Rest api
        //book some seats by calling startBooking and CompleteBooking REST APIs
	    //call updateTheatreShows api by providing new show date for the show Id
        //verify this by steps in test case getTheatreShowsByTheatreNameAndCity to see updated showDate for the above show Id
        //also verify notifyUsersViaSMS method is called one or more times (number decided by how many users booked tickets for this show)
    }
	
	
	@Test
	void updateExistingShowTimeInTheatre() {
		
	    //follow same steps as in test case updateExistingShowDateInTheatre but should update only showTime instead of showDate
	}
	
	@Test
    void bookTicketsAndThenPerformUpdateShowTimeForExistingShow() {

        //follow same steps as in test case bookTicketsAndThenPerformUpdateExistingShowDateForSpecificShow but should update only showTime
    }
	
	
	@Test
    void updateExistingShowTimeShowDateInTheatre() {
        
        //follow same steps as in test case updateExistingShowDateInTheatre but should update both showTime and showDate
    }
    
    @Test
    void bookTicketsAndThenPerformUpdateShowTimeShowDateForExistingShow() {

        //follow same steps as in test case bookTicketsAndThenPerformUpdateShowDateForExistingShow but should update both showTime and showDate
    }
	
    
    @Test
    void updateExistingMovieAssignmentInTheatre() {
        
        //follow same steps as in test case updateExistingShowDateInTheatre but should only try to update with another movie (assignment)
    }
	
    @Test
    void bookTicketsAndThenPerformUpdateMovieAssignmentForExistingShow() {

        //follow same steps as in test case bookTicketsAndThenPerformUpdateShowDateForExistingShow but should update showTime and showDate and movie assignment
        //Outcome expected : throw error because users main priority to book show tickets is their movie they selected while booking. Now since new movie is trying to get assigned when there are booked tickets, API shouldnt allow it. 
                           //Ideal solution should be is to remove the show and then add as a new show
    }
    
	
	
	
}
