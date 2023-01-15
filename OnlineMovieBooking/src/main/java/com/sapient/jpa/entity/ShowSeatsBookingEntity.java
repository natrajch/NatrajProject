package com.sapient.jpa.entity;

import static com.sapient.constants.Constants.ACTIVE;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "SHOW_SEATS_BOOKING" , uniqueConstraints = @UniqueConstraint(columnNames={"SHOW_ID", "SEAT_ID"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSeatsBookingEntity {
	
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "SHOW_ID", referencedColumnName = "ID")
	private ShowEntity show;
	
	@ManyToOne
	@JoinColumn(name = "SEAT_ID", referencedColumnName = "ID")
	private SeatEntity seat;
	
	@Column(name = "IS_ACTIVE", insertable = true, updatable = false, length = 1, nullable = true)
	@Where(clause = "is_Active = 'Y'")
	private String isActive;
	
	@ManyToOne
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
	private OrderEntity order;
	
	@Column(name = "CREATE_DT")
	@CreationTimestamp
	private LocalDateTime createDate;
	
	@Column(name = "UPDATE_DT")
	@UpdateTimestamp
	private LocalDateTime updateDate;
	
	@PrePersist
	void saveDefaultDuringCreation() {
		isActive = ACTIVE;
	}
}
