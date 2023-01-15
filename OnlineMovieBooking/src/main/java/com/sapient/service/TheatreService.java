package com.sapient.service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sapient.dto.Seat;
import com.sapient.dto.Show;
import com.sapient.dto.TheatreDto;
import com.sapient.dto.TheatreShows;
import com.sapient.enums.Category;
import com.sapient.exeption.ApplicationException;
import com.sapient.exeption.NotFoundException;
import com.sapient.jpa.entity.MovieEntity;
import com.sapient.jpa.entity.SeatEntity;
import com.sapient.jpa.entity.ShowEntity;
import com.sapient.jpa.entity.TheatreEntity;
import com.sapient.jpa.repository.MovieRepository;
import com.sapient.jpa.repository.ShowRepository;
import com.sapient.jpa.repository.TheatreRepository;
import com.sapient.mapper.TheatreEntitytoTheatreMapper;
import static com.sapient.util.CommonUtil.safe;

import lombok.extern.slf4j.Slf4j;


/*
 * This class is responsible to retrieve theatre details for the given theatre and update/add/delete theatre
 * 
 * @author Nataj Chigarambatla
 * 
 * Intial version : 20-4-2022
 */

@Service
@Slf4j
public class TheatreService {

	@Autowired
	TheatreRepository theatreRepository;
	
	
	public TheatreDto getTheatreDetails(String name, String city) {
		TheatreEntity theatreEntity = null;
		try {
			theatreEntity = theatreRepository.findByNameAndCity(name, city);
		}catch (EntityNotFoundException e){
			log.error(String.format("Theatre do not exist for input name %s and city %s"));
			return TheatreDto.builder().build();
		}
		TheatreDto dto = TheatreEntitytoTheatreMapper.INSTANCE.getTheatreDtoFromTheatreEntity(theatreEntity) ;
		return dto;
	}
	
	
	
}
