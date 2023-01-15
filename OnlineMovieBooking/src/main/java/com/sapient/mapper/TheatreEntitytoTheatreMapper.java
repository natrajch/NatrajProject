package com.sapient.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.sapient.dto.TheatreDto;
import com.sapient.jpa.entity.TheatreEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL )
public interface TheatreEntitytoTheatreMapper {

	TheatreEntitytoTheatreMapper INSTANCE  =  Mappers.getMapper(TheatreEntitytoTheatreMapper.class);
	
	@Named("theatreDtoToTheatreMapper")
	@Mapping(source = "theatreEntity.name", target = "name")
	@Mapping(source = "theatreEntity.address", target = "address")
	@Mapping(source = "theatreEntity.city", target = "city")
	@Mapping(source = "theatreEntity.countryCode", target = "country")
	TheatreDto getTheatreDtoFromTheatreEntity(TheatreEntity theatreEntity);
	
	
	@IterableMapping(qualifiedByName = "theatreDtoToTheatreMapper")
	List<TheatreDto> getTheatreListByTheatreEntites(List<TheatreEntity> theatreEntities);
	
	@InheritInverseConfiguration
	TheatreEntity getTheatreEntityFromTheatreDto(TheatreDto theatreDto);
	
}
