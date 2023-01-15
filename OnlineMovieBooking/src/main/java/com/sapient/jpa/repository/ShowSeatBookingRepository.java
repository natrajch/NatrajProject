package com.sapient.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sapient.jpa.entity.SeatEntity;
import com.sapient.jpa.entity.ShowEntity;
import com.sapient.jpa.entity.ShowSeatsBookingEntity;

@Repository
public interface ShowSeatBookingRepository  extends JpaRepository<ShowSeatsBookingEntity, Long>{
	
	@Query("select count(s)>0 from ShowSeatsBookingEntity s where s.show.id = :showId")
	Boolean isShowContainsBookedSeats(@Param("showId") long showId);

	List<ShowSeatsBookingEntity>  findAllByShowAndSeatIn(ShowEntity showEntity, List<SeatEntity> seats);

	List<ShowSeatsBookingEntity> findAllByShowIn(List<ShowEntity> updatedShows);

	List<ShowSeatsBookingEntity> findAllByShow(ShowEntity existingShow);

}
