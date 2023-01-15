package com.sapient.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.sapient.dto.Seat;
import com.sapient.jpa.entity.SeatEntity;



@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL )
public interface SeatEntityToSeatMapper {
	
	SeatEntityToSeatMapper INSTANCE  =  Mappers.getMapper(SeatEntityToSeatMapper.class);
	
	@Named("seatEntityToSeatMapper")
	@Mapping(source = "seatEntity.id", target = "seatInternalId")
	@Mapping(source = "seatEntity.number", target = "seatNumber")
	@Mapping(source = "seatEntity.category", target = "seatCategory")
	Seat getShowDtoFromShowEntity(SeatEntity seatEntity);
	
	
	@IterableMapping(qualifiedByName = "seatEntityToSeatMapper")
	List<Seat> getSeatListBySeatEntites(List<SeatEntity> seatEntities);
}
