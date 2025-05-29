package com.temple.smsservice;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Service;

import com.temple.Enteringotpmodel;

import com.temple.entity.SmsEntity;
import com.temple.repo.SmsRepo;


@Service
public class SmsService {
	@Autowired
	public SmsRepo smsrepo;

	public void saveotptodb(Integer otp ) {
		SmsEntity ent = new SmsEntity();
		ent.setOtp(otp);
		smsrepo.save(ent);
	}

	public Enteringotpmodel  userlogin(Integer receivedotp) {
		SmsEntity userdata  = smsrepo.findByOtp(receivedotp);
		Enteringotpmodel alumnidto=null;
		if(userdata != null) {
			 alumnidto = new Enteringotpmodel();
			alumnidto.setReceivedotp(receivedotp);
			
		}
		return alumnidto;
		
	}
	

	
	
	
	
	
}
