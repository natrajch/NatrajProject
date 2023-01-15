package com.sapient.service;

import static com.sapient.util.CommonUtil.safe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sapient.dto.Seat;
import com.sapient.dto.Show;
import com.sapient.dto.ShowDetails;
import com.sapient.dto.TheatreShows;
import com.sapient.dto.TheatreShowsRequest;
import com.sapient.dto.Ticket;
import com.sapient.exeption.ApplicationException;
import com.sapient.exeption.NotFoundException;
import com.sapient.jpa.entity.MovieEntity;
import com.sapient.jpa.entity.ShowEntity;
import com.sapient.jpa.entity.ShowSeatsBookingEntity;
import com.sapient.jpa.entity.TheatreEntity;
import com.sapient.jpa.repository.MovieRepository;
import com.sapient.jpa.repository.ShowRepository;
import com.sapient.jpa.repository.ShowSeatBookingRepository;
import com.sapient.jpa.repository.TheatreRepository;
import com.sapient.mapper.SeatEntityToSeatMapper;
import com.sapient.mapper.ShowEntitytoShowMapper;

import lombok.extern.slf4j.Slf4j;


/*
 * This class is responsible to retrieve/add/delete/update movie shows
 * 
 * @author Nataj Chigarambatla
 * 
 * Intial version : 20-4-2022
 */

@Service
@Slf4j
public class ShowService {

	@Autowired
	TheatreRepository theatreRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	ShowSeatBookingRepository showSeatBookingRepository;
	
	@Autowired
	TicketService ticketService;
	
	@Value ("${user.notification.message.updatedshow}")
	private String updatedShowNotificationMessage;
	
	@Value ("${user.notification.message.canceledshow}")
	private String canceledShowNotificationMessage;
	
	/*
	 * Description : To add new shows for the given theatre
	 */
	public Integer addShows(TheatreShows theatreShows) {
			
		TheatreEntity theatreEntity = theatreRepository.findByNameAndCity(theatreShows.getTheatreName(), theatreShows.getTheatreCity());
		
		if(theatreEntity==null) {
			log.error("Theatre with given name %1 and city %2 doesn't exist in the database", theatreShows.getTheatreName(), theatreShows.getTheatreCity());
			throw new NotFoundException("Theatre doesn't exist", String.format("Theatre with given name %s and city %s doesn't exist in the database", theatreShows.getTheatreName(), theatreShows.getTheatreCity()));
		}
		
		List<ShowEntity> newShows = new ArrayList<ShowEntity>();
		
		List<ShowEntity> existingShows = theatreEntity.getShows();
		
		for(Show inputShow : safe(theatreShows.getShows())) {
			
			List<ShowEntity> duplicateShowsEntities = existingShows.stream().filter(existingShow -> existingShow.getShowTime().equals(inputShow.getShowTime()) && 
																								    existingShow.getShowDate().toString().equals(inputShow.getShowDate()) &&
																								    existingShow.getTheatre().getName().equals(theatreEntity.getName()) && 
																								    existingShow.getTheatre().getCity().equals(theatreEntity.getCity()) 
																								    //not reqd movie check as assumption made that each theatre will have unique ShowTime/Showdate
																				   ).collect(Collectors.toList());
			
			if( duplicateShowsEntities!= null) {
				
				List<Show> duplicateShows = ShowEntitytoShowMapper.INSTANCE.getShowListByShowEntites(duplicateShowsEntities);
				log.error("There are already existing Show(s) "+duplicateShows+". Kindly remove existing show before prceeding to add show");
				throw new ApplicationException("Duplicate Show(s)", "There are already existing Show(s) "+duplicateShows+". Kindly remove the existing Show(s) before prceeding to add new show(s)");
			}
			
			
			MovieEntity movieEntity = createandGetMovieInDbIfDontExist(inputShow.getMovieName(), inputShow.getMovieLanguage());
			
			ShowEntity newShowEntity = ShowEntity.builder().showDate(LocalDate.parse(inputShow.getShowDate())).showTime(inputShow.getShowTime()).theatre(theatreEntity).movie(movieEntity).build();
			
			newShows.add(newShowEntity);
		}
		
		List<ShowEntity> savedShows = showRepository.saveAll(newShows);
		return savedShows.size();
	}
	
	
	/*
	 * Description : To add remove existing shows for the given theatre
	 * Assumptions : Not mandatory to provide the showId as we can query based on the showTime and ShowDate which represent unique combination
	 * 
	 */
	public Integer removeShows(TheatreShows theatreShows) {
		
		TheatreEntity theatreEntity = theatreRepository.findByNameAndCity(theatreShows.getTheatreName(), theatreShows.getTheatreCity());
		
		if(theatreEntity==null) {
			log.error("Theatre with given name %1 and city %2 doesn't exist in the database", theatreShows.getTheatreName(), theatreShows.getTheatreCity());
			throw new NotFoundException("Theatre doesn't exist", String.format("Theatre with given name %s and city %s doesn't exist in the database", theatreShows.getTheatreName(), theatreShows.getTheatreCity()));
		}
		
		final List<ShowEntity> deleteShows =  new ArrayList<ShowEntity>();
		Map<Long, Ticket> usersNotificationMap = new HashMap<Long, Ticket>();
		
		theatreShows.getShows().forEach(inputShow -> {
			
			ShowEntity existingShow = theatreEntity.getShows().stream().filter( dbShow -> dbShow.getShowTime().equals(inputShow.getShowTime()) && dbShow.getShowDate().equals(LocalDate.parse(inputShow.getShowDate()))
											  		  && dbShow.getMovie().getName().equalsIgnoreCase(inputShow.getMovieName()) && dbShow.getMovie().getLanguage().equalsIgnoreCase(inputShow.getMovieLanguage())
											  		  && dbShow.getTheatre().getName().equals(theatreEntity.getName())  &&  dbShow.getTheatre().getCity().equals(theatreEntity.getCity())
					  								).findFirst().get();
          
            if(existingShow!=null) {
            	deleteShows.add(existingShow);
            }else {
            	throw new ApplicationException("Show does not exist", inputShow.toStringCustom()+" does not exist in database" );
            }
					
		});
	
		//For each show in the deleteShows , we need to query ShowSeatsBookingEntity to check for any already booked seats for the provided shows. If records exist then proceed as below
		//step 1 : cancel all tickets of those shows internally which also initiate refund
		//step 2 : notify users stating ticket being cancelled and will be refunded
		
		List<ShowDetails> showDetailsList = new ArrayList<ShowDetails>();
		deleteShows.forEach(deleteShowEntity -> {
			 
			 if(showSeatBookingRepository.isShowContainsBookedSeats(deleteShowEntity.getId())) {
				 
				 showDetailsList.add(ShowDetails.builder().movieLanguage(deleteShowEntity.getMovie().getLanguage()).movieName(deleteShowEntity.getMovie().getName())
														  .theatreCity(deleteShowEntity.getTheatre().getCity()).theatreName(deleteShowEntity.getTheatre().getName())
														  .showTime(deleteShowEntity.getShowTime()).showDate(deleteShowEntity.getShowDate().toString()).build());
				 
				 addtoUserNotificationMap(usersNotificationMap, deleteShowEntity);
			 }
		});
		
		if( ! CollectionUtils.isEmpty(showDetailsList)) {
			
			ticketService.cancelMultipleShowsTickets(showDetailsList); 
		}
		
		deleteShows.forEach(s -> s.setIsActive("N"));		
		int affectedRows = showRepository.saveAll(deleteShows).size();
		
		if( ! CollectionUtils.isEmpty(usersNotificationMap)) {
		    //notifyUsersViaSMS(canceledShowNotificationMessage, usersNotificationMap);
		}
		
		return affectedRows;
	}


	
	private Map<Long, Ticket> addtoUserNotificationMap(Map<Long, Ticket> usersNotificationMap, ShowEntity showEntity){
		 List<ShowSeatsBookingEntity> bookedShowSeats = showSeatBookingRepository.findAllByShow(showEntity);
			
		 ShowEntity bookedShow = bookedShowSeats.get(0).getShow();
		 Ticket ticket = Ticket.builder().ticketId(bookedShowSeats.get(0).getOrder().getTicketEntity().getId()).showDate(bookedShow.getShowDate().toString())
										.showTime(bookedShow.getShowTime()).movieName(bookedShow.getMovie().getName())
										.seatNumbers(String.join(",", bookedShowSeats.stream().map(b -> b.getSeat().getNumber()).collect(Collectors.toList())))
										.build();
		
		 long mobileNumber  = bookedShowSeats.get(0).getOrder().getUser().getMobileNum();
		 usersNotificationMap.put(mobileNumber, ticket);
		 return usersNotificationMap;
	 }
	
	
	/*
	 * Description : To update the show information such as show time , show date , movie assignment 
	 * Assumptions : It is assumed that client will first query to get showId and is then supplied in the input for reference to modification
	 * 
	 */
	public Integer updateShows(TheatreShows theatreShows) {
		
		List<Long> showIds = theatreShows.getShows().stream().map(Show :: getShowId).collect(Collectors.toList());
		List<ShowEntity> existingShows  =  showRepository.findAllById(showIds);
		Map<Long, ShowEntity> existingShowsMap = existingShows.stream().collect(Collectors.toMap(s -> s.getId(), s->s));
		Map<Long, Ticket> usersNotificationMap = new HashMap<Long, Ticket>();
		
		theatreShows.getShows().forEach(inputShow ->  {
			
			if(existingShowsMap.containsKey(inputShow.getShowId())){
				
				ShowEntity existingShow = existingShowsMap.get(inputShow.getShowId());
				existingShow.setShowDate(LocalDate.parse(inputShow.getShowDate()));
				existingShow.setShowTime(inputShow.getShowTime());
				
				if(showSeatBookingRepository.isShowContainsBookedSeats(inputShow.getShowId())) {
				
					if( !(existingShow.getMovie().getName().equals(inputShow.getMovieName()) || existingShow.getMovie().getLanguage().equals(inputShow.getMovieName()))) {
						
						throw new ApplicationException("Movie assignment cannot be done", "There are already tickets booked for show "+inputShow.toString()+". Please cancel all those tickets before you update movie details for the show");
					}
					
					addtoUserNotificationMap(usersNotificationMap, existingShow);
				}
				
				MovieEntity movieEntity = createandGetMovieInDbIfDontExist(inputShow.getMovieName(), inputShow.getMovieLanguage());
				existingShow.setMovie(movieEntity);		
			}
		});
		
		int affectRows = showRepository.saveAll(existingShowsMap.values()).size();
		
		if( ! CollectionUtils.isEmpty(usersNotificationMap)) {
		    //notifyUsersViaSMS(updatedShowNotificationMessage, usersNotificationMap);
		}
		return affectRows;
	}
	
	
	protected MovieEntity createandGetMovieInDbIfDontExist(String movieName, String movieLangauage){
		
		MovieEntity movieEntity = movieRepository.findByNameAndLanguage(movieName, movieLangauage);
		
		if(movieEntity==null) {
			movieEntity = MovieEntity.builder().name(movieName).language(movieLangauage).build();
			movieEntity = movieRepository.save(movieEntity);
		}
		return movieEntity;
	}


	public List<TheatreShows> getTheatresByMovieShowSelected(TheatreShowsRequest theatreShowsRequest) {
		
		MovieEntity movieEntity = null;
		try {
			movieEntity = movieRepository.findByNameAndLanguage(theatreShowsRequest.getMovieName(), theatreShowsRequest.getMovieLanguage());
		}catch (EntityNotFoundException e){
			log.error(String.format("Movie with given name %s and language %s doesn't exist in the database",theatreShowsRequest.getMovieName(), theatreShowsRequest.getMovieLanguage()), e);
			//throw new NotFoundException("Movie doesn't exist", String.format("Movie with given name %s and language %s doesn't exist in the database",theatreShowsRequest.getMovieName(), theatreShowsRequest.getMovieLanguage()));
			return new ArrayList<TheatreShows>();
		}
		List<ShowEntity> showEntities = movieEntity.getShows().stream().filter(s-> s.getShowDate().toString().equals(theatreShowsRequest.getShowDate()) &&
																				   s.getShowTime().equals(theatreShowsRequest.getShowDate()))
																	   .collect(Collectors.toList()); 
		
		return groupShowsByTheatre(showEntities, theatreShowsRequest.getCity());
	}

	
	private List<TheatreShows> groupShowsByTheatre(List<ShowEntity> showEntities, String city){
		List<TheatreShows> theatreShowsList = new ArrayList<TheatreShows>();
		Map<Long, List<ShowEntity>> theatreShowsEntitiesMap = showEntities.stream().filter(s-> s.getTheatre().getCity().equals(city))
				                                                                   .collect(Collectors.groupingBy(s->s.getTheatre().getId()));
		
		for(Long theatreId : theatreShowsEntitiesMap.keySet() ) {
			
			TheatreEntity theatreEntity = theatreRepository.findById(theatreId).get();
			TheatreShows theatreShows = TheatreShows.builder().theatreName(theatreEntity.getName()).theatreCity(theatreEntity.getCity()).build();
			List<Show> shows =  new ArrayList<Show>();
			
			theatreShowsEntitiesMap.get(theatreId).forEach(showEntity -> {
				
				List<Long> bookedSeatIds = showEntity.getBookedSeats().stream().map(showSeatsBookingEntity-> showSeatsBookingEntity.getSeat().getId()).collect(Collectors.toList());
				Show show = ShowEntitytoShowMapper.INSTANCE.getShowDtoFromShowEntity(showEntity) ;
				List<Seat> seats = SeatEntityToSeatMapper.INSTANCE.getSeatListBySeatEntites(theatreEntity.getSeats());
				
				if( ! CollectionUtils.isEmpty(bookedSeatIds)){
					seats.forEach(seat -> {
						if(bookedSeatIds.contains(seat.getSeatInternalId())) {
							 seat.setBooked(true);
						}
					});
				}
				show.setSeats(seats);
				shows.add(show);
			});
			
			theatreShows.setShows(shows);
			theatreShowsList.add(theatreShows);
		}
		return theatreShowsList;
	}
	

	public TheatreShows getTheatreShows(String theatreName, String theatreCity, String showDate) {
		TheatreEntity theatreEntity = null;
		try {
			theatreEntity = theatreRepository.findByNameAndCity(theatreName, theatreCity);
		}catch (EntityNotFoundException e){
			log.error( String.format("Theatre with given name %s and city %s doesn't exist in the database",theatreName, theatreCity),e);
			//throw new NotFoundException("Theatre doesn't exist", String.format("Theatre with given name %s and city %s doesn't exist in the database",theatreName, theatreCity));
			return TheatreShows.builder().build();
		}
		
		List<ShowEntity> showEntities = theatreEntity.getShows().stream().filter(s-> s.getShowDate().toString().equals(showDate)).collect(Collectors.toList()); 

		return groupShowsByTheatre(showEntities, theatreCity).get(0);
	}

}
