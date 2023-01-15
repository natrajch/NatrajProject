package com.sapient.jpa.entity;

import java.io.Serializable;
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
@Table (name = "USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable{
 
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MOVIE_BOOKING_SEQ")
	private long id;
	
	@Column(name = "NAME",insertable = true, updatable = true, length = 20, nullable = false)
	private String name;
	
	@Column(name = "EMAIL", unique = true, insertable = true, updatable = true, length = 50, nullable = false)
	private String email;
	
	@Column(name = "MOBILE_NUM",unique = true, insertable = true, updatable = true, length = 10, nullable = false)
	private long mobileNum;
	
	@Column(name = "PASSWORD", unique = true, insertable = true, updatable = true, length = 100, nullable = false)
	private String password;
	
	@Column(name = "CREATE_DT")
	private LocalDate createDate;
	
	@Column(name = "UPDATE_DT")
	private LocalDate updateDate;
	
	@OneToMany
	private List<UserReviewEntity> userReviews;
	
	@PrePersist
	void saveDuringCreation() {
		createDate = LocalDate.now();
	}
	
	@PreUpdate
	void saveDuringUpdate() {
		updateDate = LocalDate.now();
	}
	
}
