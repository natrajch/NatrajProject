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

import com.sapient.dto.Show;
import com.sapient.dto.TheatreDto;
import com.sapient.jpa.entity.ShowEntity;
import com.sapient.jpa.entity.TheatreEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL )
public interface ShowEntitytoShowMapper {

	ShowEntitytoShowMapper INSTANCE  =  Mappers.getMapper(ShowEntitytoShowMapper.class);
	
	@Named("showEntityToShowMapper")
	@Mapping(source = "showEntity.id", target = "showId")
	@Mapping(source = "showEntity.showTime", target = "showTime")
	@Mapping(source = "showEntity.showDate", target = "showDate")
	@Mapping(source = "showEntity.movie.name", target = "movieName")
	@Mapping(source = "showEntity.movie.language", target = "movieLanguage")
	Show getShowDtoFromShowEntity(ShowEntity showEntity);
	
	
	@IterableMapping(qualifiedByName = "showEntityToShowMapper")
	List<Show> getShowListByShowEntites(List<ShowEntity> showEntities);
	
	@InheritInverseConfiguration
	ShowEntity getShowEntityFromShowDto(Show show);
	
	
	
}
