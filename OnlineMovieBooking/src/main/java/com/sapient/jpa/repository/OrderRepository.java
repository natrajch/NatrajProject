package com.sapient.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapient.jpa.entity.OrderEntity;

@Repository
public interface OrderRepository  extends JpaRepository<OrderEntity, Long>{

	OrderEntity findByGatewayRefIdAndOrderId(String thirdPartyRefId, Long orderId);

	List<OrderEntity> findAllByBookingStatus(String initiatedOrderBookingStatus);
	
	
}
