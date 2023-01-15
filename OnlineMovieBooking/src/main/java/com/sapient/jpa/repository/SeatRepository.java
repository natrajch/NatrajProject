package com.sapient.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapient.jpa.entity.SeatEntity;

public interface SeatRepository  extends JpaRepository<SeatEntity, Long>{

	List<SeatEntity> findAllByNumberIn(List<String> seatNumbers);

}
