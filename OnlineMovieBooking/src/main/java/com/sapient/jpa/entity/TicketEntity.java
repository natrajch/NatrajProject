package com.sapient.jpa.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "TICKET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MOVIE_BOOKING_SEQ")
	private long id;
	 
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private OrderEntity order;
	
	@Column(name = "CANCEL_REF_ID", unique = true, insertable = false, updatable = true, length = 10, nullable = true)
	private long cancelRefId;
	
	@Column(name = "REFUND_STATUS", unique = true, insertable = false, updatable = true, length = 10, nullable = true)
	private String refundStatus;
	
	@Column(name = "REFUND_DT")
	private LocalDateTime refundDate;
	
	@Column(name = "CREATE_DT")
	@CreationTimestamp
	private LocalDateTime createDate;
	
	@Column(name = "UPDATE_DT")
	@UpdateTimestamp
	private LocalDateTime updateDate;
	
}
