package com.sapient.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sapient.jpa.entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Long>{

	@Query(value = "SELECT MOVIE_BOOKING_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
	long getSeqenceGeneratedInternalIdentifier();
	

}
