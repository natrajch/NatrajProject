package com.sapient.controller;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.sapient.dto.TheatreShows;
import com.sapient.dto.TheatreShowsRequest;
import com.sapient.service.ShowService;


@RestController
public class ShowController {

	
	@Autowired
	ShowService showService;
	
	
	@ApiOperation(value = "This method is responsible to add new shows to the existing theatre", httpMethod = "PUT", response = Boolean.class)
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Exception occurred while execution"), @ApiResponse(code = 400, message = "UnAuthorized"),
			 			   @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 201, message = "Shows added successfully")})
	@PutMapping(name = "/theatre/shows")
	public ResponseEntity<EntityModel<Boolean>> addTheatreShows(@RequestBody TheatreShows theatreShows) {
		
		Boolean result =  showService.addShows(theatreShows) > 0 ? true : false;
		
		EntityModel<Boolean> resource = EntityModel.of(result);
		
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getTheatreShowsByTheatreNameAndCity(theatreShows.getTheatreName(), theatreShows.getTheatreCity(), theatreShows.getShows().get(0).getShowDate()));
		
		resource.add(linkTo.withRel("/theatre/shows"));  //HATEOAS
		
		return result ? new ResponseEntity<EntityModel<Boolean>>(resource, HttpStatus.CREATED) : new ResponseEntity<EntityModel<Boolean>>(resource, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ApiOperation(value = "This method is responsible to remove existing shows from the theatre", httpMethod = "DELETE", response = Boolean.class)
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Exception occurred while execution"), @ApiResponse(code = 400, message = "UnAuthorized"),
						   @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 200, message = "Shows deleted successfully")})
	@DeleteMapping("/theatre/shows")
	public ResponseEntity<Boolean> removeTheatreShows(@RequestBody TheatreShows theatreShows) {
		
		return showService.removeShows(theatreShows) > 0 ? new ResponseEntity<Boolean>(true, HttpStatus.OK) : new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiOperation(value = "This method is responsible to update existing shows such as showTime, ShowDate, Movie for the theatre", httpMethod = "POST", response = Boolean.class)
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Exception occurred while execution"), @ApiResponse(code = 400, message = "UnAuthorized"),
			   			   @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 200, message = "Shows updated successfully")})
	@PostMapping("/theatre/shows")
	public ResponseEntity<Boolean> updateTheatreShows(@RequestBody TheatreShows theatreShows) {
		
		return showService.updateShows(theatreShows) > 0 ? new ResponseEntity<Boolean>(true, HttpStatus.OK) : new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//use this Get to build request for performing Create/Delete/Update transactions
	@ApiOperation(value = "This method is responsible to get all the shows for the given theatre name, city and showDate", httpMethod = "GET", response = TheatreShows.class)
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Exception occurred while execution"), @ApiResponse(code = 400, message = "UnAuthorized"),
			               @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 200, message = "Data for shows fetched successfully")})
	@GetMapping("/theatre/shows")
	public ResponseEntity<TheatreShows> getTheatreShowsByTheatreNameAndCity(@RequestParam String theatreName, @RequestParam String theatreCity,  @RequestParam String showDate) {
		
		return  new ResponseEntity<TheatreShows>(showService.getTheatreShows(theatreName, theatreCity, showDate), HttpStatus.OK) ;
	}
	
	@ApiOperation(value = "This method is responsible to get all shows of all theatres for the given movie name, language, city and showdate", httpMethod = "GET", response = TheatreShows.class)
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Exception occurred while execution"), @ApiResponse(code = 400, message = "UnAuthorized"),
			               @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 200, message = "Data for shows fetched successfully")})
	@GetMapping("/movie/shows")
	public ResponseEntity<List<TheatreShows>> getTheatresByMovieShowSelected(@RequestBody TheatreShowsRequest theatreShowsRequest) {
		
		return  new ResponseEntity<List<TheatreShows>>(showService.getTheatresByMovieShowSelected(theatreShowsRequest), HttpStatus.OK);
	}

}
