package com.sapient.service;

import static com.sapient.constants.Constants.IN_ACTIVE;
import static com.sapient.constants.Constants.REFUND_INTIATED_STATUS;
import static com.sapient.util.CommonUtil.isDateInPast;
import static com.sapient.util.CommonUtil.isTimeInPastToday;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sapient.dto.BulkTickets;
import com.sapient.dto.ShowDetails;
import com.sapient.exeption.ApplicationException;
import com.sapient.exeption.NotFoundException;
import com.sapient.jpa.entity.MovieEntity;
import com.sapient.jpa.entity.ShowEntity;
import com.sapient.jpa.entity.TheatreEntity;
import com.sapient.jpa.entity.TicketEntity;
import com.sapient.jpa.repository.MovieRepository;
import com.sapient.jpa.repository.ShowRepository;
import com.sapient.jpa.repository.ShowSeatBookingRepository;
import com.sapient.jpa.repository.TheatreRepository;
import com.sapient.jpa.repository.TicketRepository;

@Service
public class TicketService {
	
	
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	ShowSeatBookingRepository showSeatBookingRepository;
	
	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	TheatreRepository theatreRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	public Map<Long, Long> cancelBulkTickets(BulkTickets bulkTickets) {
		
		//parse the comma separated tickets and initiate cancellation
		//cancellation will generate and store unique CancelReferenceId and mark refund status as Initiated for each TicketEntity
		//map will contain (key-TicketId value-CancelledReferenceId)
		 Map<Long, Long> canceledTkts = new HashMap<Long, Long>();
		
        List<TicketEntity> ticketEntities = ticketRepository.findAllById(bulkTickets.getTicketIds());
		
        ticketEntities.forEach(ticketEntity -> {
        	
        	LocalDate showDate  = ticketEntity.getOrder().getShowSeats().get(0).getShow().getShowDate();
    		String    showTime  = ticketEntity.getOrder().getShowSeats().get(0).getShow().getShowTime();
    		if(isDateInPast(showDate) || isTimeInPastToday(showDate, showTime)) {
    			throw new ApplicationException("Ticket cancellation is not allowed","Movie show is already completed");
    		}
        	
        	long cancelId = ticketRepository.getSeqenceGeneratedInternalIdentifier();
        	
        	ticketEntity.setCancelRefId(cancelId);
    		
    		ticketEntity.setRefundStatus(REFUND_INTIATED_STATUS);
    		
    		ticketEntity.getOrder().getShowSeats().forEach(s -> s.setIsActive(IN_ACTIVE));
    		
    		canceledTkts.put(ticketEntity.getId(), cancelId);
        });
		
        ticketEntities = ticketRepository.saveAll(ticketEntities);
		return canceledTkts;
	}

	
	public Long cancelTicket(long ticketId) {
		
		TicketEntity ticketEntity = null;
		try {
			ticketEntity = ticketRepository.findById(ticketId).get();
		}catch(EntityNotFoundException e) {
			throw new NotFoundException("Ticket not found", "Please enter a valid ticket Id");
		}
		
		LocalDate showDate  = ticketEntity.getOrder().getShowSeats().get(0).getShow().getShowDate();
		String    showTime  = ticketEntity.getOrder().getShowSeats().get(0).getShow().getShowTime();
		if(isDateInPast(showDate) || isTimeInPastToday(showDate, showTime)) {
			throw new ApplicationException("Ticket cancellation is not allowed","Movie show is already completed");
		}
		
		ticketEntity.setCancelRefId(ticketRepository.getSeqenceGeneratedInternalIdentifier());
		
		ticketEntity.setRefundStatus(REFUND_INTIATED_STATUS);
		
		ticketEntity.getOrder().getShowSeats().forEach(s -> s.setIsActive(IN_ACTIVE));
		
		ticketEntity = ticketRepository.save(ticketEntity);
		
		return ticketEntity.getCancelRefId();
	}
	

	public Boolean cancelShowTickets(ShowDetails showDetails) {
		
		return true;
	}

	
	public Boolean cancelMultipleShowsTickets(List<ShowDetails> showDetailsList){
		
		List<ShowEntity> showEntities = new ArrayList<ShowEntity>();
		List<TicketEntity> ticketEntities =  new ArrayList<TicketEntity>();
		showDetailsList.forEach(s -> {
			TheatreEntity theatreEntity = theatreRepository.findByNameAndCity(s.getTheatreName(), s.getTheatreCity());
			MovieEntity movieEntity = movieRepository.findByNameAndLanguage(s.getMovieName(), s.getMovieLanguage());
			ShowEntity showEntity = showRepository.findByShowTimeAndShowDateAndMovieAndTheatre(s.getShowTime(), LocalDate.parse(s.getShowDate()), movieEntity, theatreEntity).get();
			showEntity.getBookedSeats().forEach(b -> {
				b.setIsActive(IN_ACTIVE);
				TicketEntity ticket = b.getOrder().getTicketEntity();
				if(ticket!=null) {
					long cancelId = ticketRepository.getSeqenceGeneratedInternalIdentifier();
					ticket.setCancelRefId(cancelId);
					ticket.setRefundStatus(REFUND_INTIATED_STATUS);
					ticketEntities.add(ticket);
				}
			});
			showEntities.add(showEntity);
		});	
		
		if( ! CollectionUtils.isEmpty(showEntities)) {
			showRepository.saveAll(showEntities);
		}
		
		if( ! CollectionUtils.isEmpty(ticketEntities)) {
			ticketRepository.saveAll(ticketEntities);
		}
		
		return true;
	}
}
