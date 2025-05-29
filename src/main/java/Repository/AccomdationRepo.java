package com.temple.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.temple.entity.AccomdationEntity;



public interface AccomdationRepo extends JpaRepository<AccomdationEntity, Integer> {

	
long countByRegisterdate(String registerdate);
	
	List<AccomdationEntity>findByRegisterdate(String registerdate);
	
	AccomdationEntity findByFullname(String fullname);
}
