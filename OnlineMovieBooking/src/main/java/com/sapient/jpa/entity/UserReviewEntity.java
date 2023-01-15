package com.sapient.jpa.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "USER_REVIEW")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReviewEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MOVIE_BOOKING_SEQ")
	private long id;
	
	@Column(name = "USER_ID",insertable = true, updatable = true, length = 10, nullable = false)
	private UserEntity user;
	
	@Column(name = "COMMENTS",insertable = true, updatable = true, length = 400, nullable = false)
	private String comments;
	
	@Column(name = "RATING",insertable = true, updatable = true, length = 2, nullable = false)
	private long rating;
	
	@Column(name = "CREATE_DT")
	private LocalDate createDate;
	
	@Column(name = "UPDATE_DT")
	private LocalDate updateDate;
	
	@PrePersist
	void saveDuringCreation() {
		createDate = LocalDate.now();
	}
	
	@PreUpdate
	void saveDuringUpdate() {
		updateDate = LocalDate.now();
	}
}
