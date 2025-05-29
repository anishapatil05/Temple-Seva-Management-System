package com.temple.smsservice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.temple.RegisterModel;
import com.temple.entity.RegisterEntity;
import com.temple.repo.RegisterRepo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


// @Service : Contains business logic (validation ,Processing )
@Service
public class RegisterService {

	public long maxDevotee = 10;
	public static final String ACCOUNT_SID = "AC027a95b68aa7db50d379ab3fced2409d";
	public static final String AUTH_TOKEN = "bce28a0367bfb1d584b9159e9e34c97c";
	public static final String from_number = "+19405033983";
    public   String to_number="";
    public Integer saveotp;
    
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

   
	@Autowired
	public RegisterRepo registerrepo;

	public void saveregisterdatestodb(RegisterModel registerModel) {

		RegisterEntity registerEntity = new RegisterEntity();

		//  ****Copy the data from register model into regiEntity
		registerEntity.setFullname(registerModel.getFullname());
		registerEntity.setEmail(registerModel.getEmail());
		registerEntity.setAge(registerModel.getAge());
		registerEntity.setMobilenumber(registerModel.getMobilenumber());
		registerEntity.setRegisterdate(registerModel.getRegisterdate());
		registerEntity.setGender(registerModel.getGender());
		registerEntity.setTimeSlot(registerModel.getTimeSlot());

//		Saves into table format in DB
		registerrepo.save(registerEntity);

	}

//   Get total Counts based on that regdate 
	public long getcountbydate(String regdate) {
		
//		countBy : automatically counts the rows from DB
		long count = registerrepo.countByRegisterdate(regdate);
		return count;
	}

	
//	Fetches all the rows from the registration table.
	public List<RegisterEntity> getAllRegistrations() {
		return registerrepo.findAll();
	}
	
//   send a confirmation SMS 
	public void sendotp(String mnumber) {
		String to_number= mnumber;
		System.out.println( to_number);
	    	
	    	 String ot = "Darshan Booking Successful!";
	  Message message = Message.creator(new PhoneNumber(to_number),new PhoneNumber(from_number), ot).create();
	     System.out.println(message.getBody());


	}

}
