package com.sapient.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sapient.dto.BookingRequest;
import com.sapient.dto.OrderParams;
import com.sapient.dto.Show;
import com.sapient.dto.Ticket;
import com.sapient.exeption.ApplicationException;
import com.sapient.jpa.entity.MovieEntity;
import com.sapient.jpa.entity.OfferEntity;
import com.sapient.jpa.entity.OrderEntity;
import com.sapient.jpa.entity.SeatEntity;
import com.sapient.jpa.entity.ShowEntity;
import com.sapient.jpa.entity.ShowSeatsBookingEntity;
import com.sapient.jpa.entity.TheatreEntity;
import com.sapient.jpa.entity.TicketEntity;
import com.sapient.jpa.entity.UserEntity;
import com.sapient.jpa.repository.MovieRepository;
import com.sapient.jpa.repository.OfferRepository;
import com.sapient.jpa.repository.OrderRepository;
import com.sapient.jpa.repository.SeatRepository;
import com.sapient.jpa.repository.ShowRepository;
import com.sapient.jpa.repository.ShowSeatBookingRepository;
import com.sapient.jpa.repository.TheatreRepository;
import com.sapient.jpa.repository.TicketRepository;
import com.sapient.jpa.repository.UserRepository;
import com.sapient.util.CommonUtil;

import static com.sapient.util.CommonUtil.*;
import static com.sapient.constants.Constants.*;

@Service
public class OrderService {
	
	@Autowired
	SeatRepository seatRepository;
	
	@Autowired
	OfferRepository offerRepository;
	
	@Autowired 
	ShowRepository showRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ShowSeatBookingRepository showSeatBookingRepository;
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	TheatreRepository theatreRepository;
	
	
	
	public String startBooking(BookingRequest bookingRequest){
		
		  if(bookingRequest.getSeatNumbers().size()!= new HashSet<String>(bookingRequest.getSeatNumbers()).size())
			  throw new ApplicationException("Duplicate Seat Numbers" , "Duplicate Seat Numbers in the request. Please send unique seat numbers");
		
		  TheatreEntity theatreEntity = theatreRepository.findByNameAndCity(bookingRequest.getTheatreName(), bookingRequest.getTheatreCity());
		  
		  MovieEntity movieEntity = movieRepository.findByNameAndLanguage(bookingRequest.getMovieName(), bookingRequest.getMovieLanguage());
	  
		  ShowEntity showEntity = showRepository.findByShowTimeAndShowDateAndMovieAndTheatre(bookingRequest.getShowTime(), LocalDate.parse(bookingRequest.getShowDate()),
				  																			 movieEntity, theatreEntity).get();
		
		  List<SeatEntity> requestedSeats = seatRepository.findAllByNumberIn(bookingRequest.getSeatNumbers());
		
		  List<ShowSeatsBookingEntity> alreadyBookedEntities = showSeatBookingRepository.findAllByShowAndSeatIn(showEntity, requestedSeats);
		  
		  if( ! CollectionUtils.isEmpty(alreadyBookedEntities)) {
			  List<String> alreadyBookedSeatNumbers = alreadyBookedEntities.stream().map(ssb -> ssb.getSeat().getNumber()).collect(Collectors.toList());
			  throw new ApplicationException("Seats Already Reserved" , String.format("Seats "+alreadyBookedSeatNumbers.toString()+" Cannot be booked again for movie %s in theatre %s", movieEntity.getName(), theatreEntity.getName() ));
		  }
		
		  UserEntity userEntity = userRepository.findById(bookingRequest.getUserId()).get();
		  
		  List<ShowSeatsBookingEntity> newBookingSeats = new ArrayList<ShowSeatsBookingEntity>();
		  
		  for(SeatEntity seat : requestedSeats) {
			  newBookingSeats.add(ShowSeatsBookingEntity.builder().show(showEntity).seat(seat).build());
		  }
		 
		  newBookingSeats = showSeatBookingRepository.saveAll(newBookingSeats);
		  
		  OrderEntity newOrder = OrderEntity.builder().amount(getTotalAmount(bookingRequest)).bookingStatus(INITIATED_ORDER_BOOKING_STATUS)
				                     				  .user(userEntity).build();
		  
		  OrderEntity createdOrder = orderRepository.save(newOrder);
		  String redirectUrl = "";                           //call third party payment gateway api by passing orderId/userId. 
		  createdOrder.setGatewayRefId("GATEWAY_REF_ID-1");  //parse redirect url returned from gateway response to store the gateway ref id in database.
		  createdOrder = orderRepository.save(createdOrder);
		  
		  for(ShowSeatsBookingEntity bookingSeat : newBookingSeats) {
			  bookingSeat.setOrder(createdOrder);
		  }
		 
		  showSeatBookingRepository.saveAll(newBookingSeats);
		  return redirectUrl;
	}
	 
	
	
	//orderParams are read from the url where user is redirected back to our application after payment activity is carried out in third party payment gateway.
	public Ticket completeBooking(OrderParams orderParams) {
		
		Ticket ticket = null;
		OrderEntity orderEntity = orderRepository.findByGatewayRefIdAndOrderId(orderParams.getThirdPartyRefId(), orderParams.getOrderId());
		List<ShowSeatsBookingEntity> bookedEntities = orderEntity.getShowSeats();
		
		//Instead of directly checking status of payment transaction based on the orderParams (as below), we should also have an option to call validateTransaction api of payment gateway by passing gateway reference id to know the transaction status
		
		if(THIRDPARTY_SUCCESS_PAYMENT_STATUS.equals(orderParams.getStatus())){
			
			orderEntity.setBookingStatus(COMPLETED_ORDER_BOOKING_STATUS);
			TicketEntity ticketEntity = ticketRepository.save(TicketEntity.builder().order(orderEntity).build());
			ticket = Ticket.builder().ticketId(ticketEntity.getId()).showTime(bookedEntities.get(0).getShow().getShowTime())
					                 .seatNumbers(String.join(",", bookedEntities.stream().map(s-> s.getSeat().getNumber()).collect(Collectors.toList())))
					                 .movieName(bookedEntities.get(0).getShow().getMovie().getName()).theatreName(bookedEntities.get(0).getShow().getTheatre().getName())
					                 .build();
			
		}else {
			bookedEntities.forEach(b->{b.setIsActive(IN_ACTIVE);});
			showSeatBookingRepository.saveAll(bookedEntities);
			orderEntity.setBookingStatus(
					THIRDPARTY_FAILED_PAYMENT_STATUS.equals(orderParams.getStatus()) ?  FAILED_ORDER_BOOKING_STATUS : CANCELLED_ORDER_BOOKING_STATUS
			);
			//throw exception with providing necessary details indicating order wasn't successful 
			throw new ApplicationException("Order not succcessful." ," Order Booking status => "+orderEntity.getBookingStatus()+ "for OrderId => "+orderEntity.getOrderId() +" was not succcessful" );
		}
		orderRepository.save(orderEntity);
		return ticket;
	}
	
	
	
	private long getTotalAmount(BookingRequest bookingRequest){
		
		List<SeatEntity> requestedSeats = seatRepository.findAllByNumberIn(bookingRequest.getSeatNumbers());
		
		int totalRequestedSeats = requestedSeats.size();
		int perSeatCost = requestedSeats.get(0).getCategory().getAmount();
		int totalAmount = totalRequestedSeats * perSeatCost;  
		
		List<OfferEntity> offers = offerRepository.findAll();
		if(!CollectionUtils.isEmpty(offers)) {
			
			Map<String, OfferEntity> offersMap = offers.stream().collect(Collectors.toMap(o-> o.getType(),o->o));
			
			TheatreEntity theatreEntity = theatreRepository.findByNameAndCity(bookingRequest.getTheatreName(), bookingRequest.getTheatreCity());
			  
			MovieEntity movieEntity = movieRepository.findByNameAndLanguage(bookingRequest.getMovieName(), bookingRequest.getMovieLanguage());
			
			ShowEntity showEntity = showRepository.findByShowTimeAndShowDateAndMovieAndTheatre(bookingRequest.getShowTime(), LocalDate.parse(bookingRequest.getShowDate()),
						 																	   movieEntity, theatreEntity).get();
			
			List<Integer> discountAmounts = new ArrayList<Integer>();
			if(offersMap.containsKey(NOON_SHOW_OFFER_TYPE) && isTimeFallsInTheRange(showEntity.getShowTime(), DAY_PART_NOON)) {
				discountAmounts.add(totalAmount * (offersMap.get(NOON_SHOW_OFFER_TYPE).getDiscountPercent()/100));
			}
			
			if(offersMap.containsKey(THIRD_TICKET_OFFER_TYPE) &&  totalRequestedSeats > 2) {
				int thirdTktOfferAmt = perSeatCost * (totalRequestedSeats/3) * (offersMap.get(THIRD_TICKET_OFFER_TYPE).getDiscountPercent()/100);
				discountAmounts.add(thirdTktOfferAmt);
			}
			
			totalAmount  = totalAmount - discountAmounts.stream().collect(Collectors.summingInt(Integer::intValue));
		 }
		return totalAmount;
	}

	
	/*
	You can configure your application to have multiple profiles.
	For example use another profile 'cron' . And start your application on only one server with this profile. 
	So for example, on a production environment you have three servers (S1, S2, S3), then you could run on S1 with profile prod and cron(-Dspring.profiles.active=prod,cron). 
	And on S2 and S3 just use prod profile(-Dspring.profiles.active=prod) 
	*/
	@Profile({"cron"})
	@Scheduled(fixedDelay = 1000 * 60 * 5)
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void unblockSeatsForLongStandingPendingOrders() {
		List<OrderEntity> pendingOrders = orderRepository.findAllByBookingStatus(INITIATED_ORDER_BOOKING_STATUS);
		for(OrderEntity pendingOrder : pendingOrders) {
			pendingOrder.setWaitCount(pendingOrder.getWaitCount() + 1);
			if(pendingOrder.getWaitCount() == MAX_REFUND_WAIT_COUNT) {
				pendingOrder.getShowSeats().forEach(b->{b.setIsActive(IN_ACTIVE);});
				pendingOrder.setBookingStatus(FAILED_ORDER_BOOKING_STATUS);
			}
		}
		pendingOrders = orderRepository.saveAll(pendingOrders);
	}
	

}
