package com.temple.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.temple.entity.SmsEntity;

public interface SmsRepo  extends JpaRepository<SmsEntity, Integer>{
	
	//SmsEntity findByUnameAndPassword(String uname,String password);

	SmsEntity findByOtp(Integer receivedotp);


}