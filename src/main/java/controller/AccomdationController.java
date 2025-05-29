package com.temple.messagecount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.temple.AccomdationModel;
import com.temple.smsservice.AccomdationService;

@Controller
public class AccomdationController {
	
	public String mnumber;

	@Autowired
	public AccomdationService accomdationservice;
	
//	Accomdation slot booking page
	@GetMapping("/accommodation")
	public String accomdationBooking()
	{
		return "Accomdation";
	}
	
// AccomdationSuccess Booking msg
	@GetMapping("/accomdationsuccess")
	public String accomdationBookingSuccess() {
		return "AccomdationSuccess";
	}
	
	// AccomdationFail Booking msg
		@GetMapping("/accomdationfail")
		public String accomdationBookingFail() {
			return "AccomdationFail";
		}
	
//	Back button of accomdation 
	
	@GetMapping("/backButtonofAccom")
	public String backButtonWorking() {
		return "RegiSuccess";
	}
	
	@PostMapping("/accommodationBooking")
	public String savetodb(@ModelAttribute AccomdationModel accomdationmodel) {
		String selectedDate = accomdationmodel.getRegisterdate();
		mnumber=accomdationmodel.getMobilenumber();
		
		long  totalBookingcount = accomdationservice.getcountbydate(selectedDate);
		System.out.println(totalBookingcount);
		System.out.println("Total Bookings for " + selectedDate);
		
		if(totalBookingcount < accomdationservice.maxDevotee) {
			accomdationservice.saveBookingDateToDb(accomdationmodel);
			accomdationservice.sendotp(mnumber);
			return "redirect:/accomdationsuccess";
		}else {
			return "redirect:/accomdationfail";
		}
	}
}
