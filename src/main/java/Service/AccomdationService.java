package com.temple.smsservice;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



import com.temple.AccomdationModel;
import com.temple.entity.AccomdationEntity;
import com.temple.repo.AccomdationRepo;

@Service
public class AccomdationService {

	
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
	public AccomdationRepo accomdationrepo;
	
	public void saveBookingDateToDb(AccomdationModel accomdationmodel) {
		AccomdationEntity accomdation = new AccomdationEntity();
		
		accomdation.setFullname(accomdationmodel.getFullname());
		accomdation.setAge(accomdationmodel.getAge());
		accomdation.setRegisterdate(accomdationmodel.getRegisterdate());
        accomdation.setEmail(accomdationmodel.getEmail());
        accomdation.setGender(accomdationmodel.getGender());
        accomdation.setAge(accomdationmodel.getAge());
        accomdation.setRoomType(accomdationmodel.getRoomType());
        accomdation.setMobilenumber(accomdationmodel.getMobilenumber());
        
        accomdationrepo.save(accomdation);
        
	}
	

	public long getcountbydate(String regdate) {
		long count = accomdationrepo.countByRegisterdate(regdate);
		return count;
	}
	
	public List<AccomdationEntity> getAllRegistrations() {
		return accomdationrepo.findAll();
	}

      public void sendotp(String mnumber) {
			String to_number= mnumber;
			System.out.println( to_number);
		    	
		    	 String ot = "Your room is Booked ";
		  Message message = Message.creator(new PhoneNumber(to_number),new PhoneNumber(from_number), ot).create();
		     System.out.println(message.getBody());


		}

}
