package com.sapient.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapient.jpa.entity.TheatreEntity;

@Repository
public interface TheatreRepository  extends JpaRepository<TheatreEntity, Long>{
	
	
	TheatreEntity findByNameAndCity(String name, String city);

}
