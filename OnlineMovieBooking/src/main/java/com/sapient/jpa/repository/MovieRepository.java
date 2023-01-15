package com.sapient.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapient.jpa.entity.MovieEntity;

@Repository
public interface MovieRepository  extends JpaRepository<MovieEntity, Long>{
	
	
	MovieEntity findByNameAndLanguage(String name, String language);

}
