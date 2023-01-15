package com.sapient.jpa.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "THEATRE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheatreEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MOVIE_BOOKING_SEQ")
	private long id;
	
	@Column(name = "NAME",insertable = true, updatable = true, length = 50, nullable = false)
	private String name;
	
	@Column(name = "ADDRESS",insertable = true, updatable = true, length = 400, nullable = false)
	private String address;
	
	@Column(name = "CITY",insertable = true, updatable = true, length = 20, nullable = false)
	private String city;
	
	@Column(name = "COUNTRY_CODE",insertable = true, updatable = true, length = 2, nullable = false)
	private String countryCode;
	
	@Column(name = "MAX_SEATS_CAPACITY",insertable = true, updatable = true, length = 3, nullable = false)
	private int maxSeatsCapacity;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "theatre")
	private List<ShowEntity> shows;
	
	@OneToMany (cascade = CascadeType.PERSIST, mappedBy = "theatre")
	@Where(clause = "is_Active = 'Y'")
	private List<SeatEntity> seats;
}
