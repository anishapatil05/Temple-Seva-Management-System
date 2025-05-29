package com.temple.messagecount;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.temple.entity.DonationEntity;
import com.temple.smsservice.DonationService;

@Controller
public class DonationController {
	public String mnumber;
	
	@Autowired	
    public DonationService donationservice;
	
	@GetMapping("/donation/create-order")
	public String init() {
		return "Donation";
	}
		
//	Donation Success
	@GetMapping("/donationSuccess")
	public String donationSuccess()
	{
		return "DonationSuccess";
	}
	
//	back button of donationSuccess
	@GetMapping("/home")
	public String DonationHomePage() {
		return "HomePage";
	}
	
//	Donation Fail
	@GetMapping("/donationFail")
	public String donationFail() {
		return "DonationFail";
	}
	
	@PostMapping(value = "/donation/create-order", produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<DonationEntity> donate(@RequestBody DonationEntity donationentity) throws Exception {

		DonationEntity donation = donationservice.createDonation(donationentity);

		return new ResponseEntity<>(donation, HttpStatus.OK);
	}

	
	@PostMapping("/donation/payment-callback")
	public String handlePaymentCallback(@RequestParam Map<String,String>resPayload,Model model) {
		 System.out.println(resPayload);
		 
		
		 
     DonationEntity updatedOrder = donationservice.verifyPaymentAndUpdateOrderStatus(resPayload);
     model.addAttribute("order",updatedOrder);
     
//         Fetch order status from DB
         String status = updatedOrder.getOrderStatus();
     
//        Redirect based on order status
     if("confirmed".equalsIgnoreCase(status)) {
    	 return "redirect:/donationSuccess";
     }else {
     return "redirect:/donationFail";
     }
	}
}


