package com.sapient.jpa.entity;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.sapient.enums.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "SEAT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MOVIE_BOOKING_SEQ")
	private long id;
	
	@Column(name = "NUMBER",insertable = true, updatable = true, length = 50, nullable = false)
	private String number;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "CLASS",insertable = true, updatable = true, length = 10, nullable = false)
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "THEATRE_ID", referencedColumnName = "ID")
	private TheatreEntity theatre;
	
	@OneToMany (cascade = CascadeType.PERSIST, mappedBy = "seat")
	@Where(clause = "is_Active = 'Y'")
	private List<ShowSeatsBookingEntity> bookedSeats;
	
}



