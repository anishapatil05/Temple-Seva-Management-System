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
import com.temple.entity.PaymentEntity;

import com.temple.smsservice.PaymentService;
@Controller
public class PaymentController {

		@Autowired	
	    public PaymentService paymentService;
	
		@GetMapping("/payment/create-order")
		public String init() {
			return "PaymentPage";
		}
		
		@GetMapping("/paymentSuccess")
		public String paymentSuccess()
		{
			return "PaymentSuccessfull";
		}
		
//		back button of donationSuccess
		@GetMapping("/homepage")
		public String paymentHomePage() {
			return "HomePage";
		}
		
		@PostMapping(value ="/payment/create-order",produces = "application/json", consumes = "application/json")
		@ResponseBody
		public ResponseEntity<PaymentEntity> donate(@RequestBody PaymentEntity paymentEntity) throws Exception {

// room reservation / max devotee condition
	        // Calculate the total amount based on the number of persons
			Integer amountPaid = 50 * paymentEntity.getPerson();
			Integer amount = paymentEntity.getAmount();
			System.out.println(paymentEntity.getOrderId());
			System.out.println(paymentEntity.getPhno());
		
			if(amountPaid==amount)
			{
				   System.out.println("anisha patil");
				   PaymentEntity donation = paymentService.createPayment(paymentEntity);
		            return new ResponseEntity<>(donation, HttpStatus.OK);
				
			}else {
				   
			}
			
			return null;
		
		}

//		change url according to index.html
		@PostMapping("/payment/payment-callback")
		public String handlePaymentCallback(@RequestParam Map<String,String>resPayload,Model model) {
			 System.out.println(resPayload);
			 
			
			 
	     PaymentEntity updatedOrder = paymentService.verifyPaymentAndUpdateOrderStatus(resPayload);
	     model.addAttribute("order",updatedOrder);
	     
//	         Fetch order status from DB
	         String status = updatedOrder.getOrderStatus();
	     
//	        Redirect based on order status
	     if("confirmed".equalsIgnoreCase(status)) {
	    	 return "redirect:/paymentSuccess";
	     }else {
	     return "redirect:/home";
	     }
		}
	}