package com.sapient.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "OFFER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferEntity {
	
	@Id
	@Column(name = "NAME")
	private String name;

	@Column(name = "DISCOUNT_PERCENT", insertable = true, updatable = true, length = 2, nullable = false)
	private Integer discountPercent;
	
	@Column(name = "TYPE")
	private String type;

	@Column(name = "IS_ACTIVE", insertable = true, updatable = true, length = 1, nullable = true)
	@Where(clause = "is_Active = 'Y'")
	private String isActive;


}
