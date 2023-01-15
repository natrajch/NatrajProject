package com.sapient.jpa.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "MOVIE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieEntity {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MOVIE_BOOKING_SEQ")
	private long id;
	
	@Column(name = "NAME",insertable = true, updatable = true, length = 100, nullable = false)
	private String name;
	
	@Column(name = "LANGUAGE",insertable = true, updatable = true, length = 10, nullable = false)
	private String language;
	
	@Column(name = "CENSOR_GRADE",insertable = true, updatable = true, length = 1, nullable = false)
	private String censorGrade;
	
	@Column(name = "CAST_CREW", insertable = true, updatable = true, nullable = false)
	@Lob
	private String castCrew;
	
	@Column(name = "IS_ACTIVE", insertable = true, updatable = true, length = 1, nullable = false)
	@Where(clause = "is_Active = 'Y'")
	private String isActive;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "movie")
	private List<ShowEntity> shows;
	
	
}
