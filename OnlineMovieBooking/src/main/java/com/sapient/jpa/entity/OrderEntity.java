package com.sapient.jpa.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

	
	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MOVIE_BOOKING_SEQ")
	private long orderId;
	
	@Column(name = "GATEWAY_REF_ID",insertable = true, updatable = true, length = 20, nullable = false)
	private String gatewayRefId;
	
	@Column(name = "BOOKING_STATUS",insertable = true, updatable = true, length = 10, nullable = false)
	private String bookingStatus;
	
	@Column(name = "AMOUNT",insertable = true, updatable = true, length = 4, nullable = false)
	private long amount;  
	
	@ManyToOne
	@JoinColumn(name = "BOOKED_USER_ID", referencedColumnName = "ID")
	private UserEntity user;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "order")
	private List<ShowSeatsBookingEntity> showSeats;
	
	@OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "order")
	private TicketEntity ticketEntity;
	
	@Column(name = "WAIT_COUNT",insertable = true, updatable = true, length = 1, nullable = true)
	private int waitCount;  
	
	@Column(name = "CREATE_DT")
	@CreationTimestamp
	private LocalDateTime createDate;
	
	@Column(name = "UPDATE_DT")
	@UpdateTimestamp
	private LocalDateTime updateDate;
}
