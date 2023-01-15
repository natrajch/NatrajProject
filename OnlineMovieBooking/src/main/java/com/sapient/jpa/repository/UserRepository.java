package com.sapient.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapient.jpa.entity.OfferEntity;
import com.sapient.jpa.entity.UserEntity;

public interface UserRepository  extends JpaRepository<UserEntity, Long>{

}
