package com.sapient.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapient.jpa.entity.MovieEntity;
import com.sapient.jpa.entity.ShowEntity;
import com.sapient.jpa.entity.TheatreEntity;

@Repository
public interface ShowRepository  extends JpaRepository<ShowEntity, Long>{

	List<ShowEntity> findAllByMovieAndShowDate(MovieEntity movie, LocalDate showDate);

	Optional<ShowEntity> findByShowTimeAndShowDateAndMovieAndTheatre(String showTime, LocalDate showDate, MovieEntity movie, TheatreEntity theatre);

	

}
