package com.temple.messagecount;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.temple.RegisterModel;
import com.temple.smsservice.*;

@Controller
public class RegisterController {

	public String mnumber;
	
	@Autowired
	public RegisterService registerservice;

//	  ******   Darshan Success Page**** 
//	Darshan slot booking Page
	@GetMapping("/darshan")
	public String darshanBooking() {
		return "DarshanBooking";
	}

//  Back button of darshanbooking
	@GetMapping("/backButtondarshan")
	public String backbuttonWorking() {
		return "RegiSuccess";
	}

	@GetMapping("/darshanSuccess")
	public String DarshanSuccess()
	{
		return "DarshanBookingSuccess";
	}
	@PostMapping("/darshanBooking")
	public String savetodb(@ModelAttribute RegisterModel registerModel) {
	    String selectedDate = registerModel.getRegisterdate();
		mnumber=registerModel.getMobilenumber();

	    long totalBookingCount = registerservice.getcountbydate(selectedDate);
	    System.out.println(totalBookingCount);

	    // Check if the booking count for the selected date is less than the max limit
	    if (totalBookingCount < registerservice.maxDevotee) {
	    	
	        // Save the registration 
	    	registerservice.saveregisterdatestodb(registerModel);
	    	registerservice.sendotp(mnumber);
	        return "redirect:/darshanSuccess";  // Redirect to the success page
	    } else {
	        return "RegiFail";  // Redirect to the failure page
	    }
	}

}
