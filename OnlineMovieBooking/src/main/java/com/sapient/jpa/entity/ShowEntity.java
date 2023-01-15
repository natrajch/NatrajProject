package com.sapient.jpa.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "SHOW", uniqueConstraints = @UniqueConstraint(columnNames={"SHOW_TIME", "SHOW_DATE", "THEATRE_ID"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MOVIE_BOOKING_SEQ")
	private long id;
	
	@Column(name = "SHOW_TIME",insertable = true, updatable = true, length = 4, nullable = false)
	private String showTime;
	
	@Column(name = "SHOW_DATE",insertable = true, updatable = true, nullable = false)
	private LocalDate showDate;
	
	@ManyToOne
	@JoinColumn(name = "MOVIE_ID", referencedColumnName = "ID")
	private MovieEntity movie;
	
	@ManyToOne
	@JoinColumn(name = "THEATRE_ID", referencedColumnName = "ID")
	private TheatreEntity theatre;

	@Column(name = "IS_ACTIVE", insertable = true, updatable = false, length = 1, nullable = false)
	private String isActive;
	
	@OneToMany (cascade = CascadeType.PERSIST, mappedBy = "show")
	@Where(clause = "is_Active = 'Y'")
	private List<ShowSeatsBookingEntity> bookedSeats;
	
	
	
}
