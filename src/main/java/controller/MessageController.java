package com.temple.messagecount;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.temple.Enteringotpmodel;
import com.temple.Smsmodel;
import com.temple.smsservice.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Controller
public class MessageController {
	@Autowired
	public SmsService smsservice;


	public static final String ACCOUNT_SID = "AC027a95b68aa7db50d379ab3fced2409d";
	public static final String AUTH_TOKEN = "bce28a0367bfb1d584b9159e9e34c97c";
	public static final String from_number = "+19405033983";
	public String to_number = "";
	public Integer saveotp;

	static {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	}

	@GetMapping("/")
	public String homepage() {
		return "HomePage";
	}

	@PostMapping("/generateotp")
	public String getOpt(@ModelAttribute Smsmodel smsmodel) {
		String to_number = smsmodel.getFnumber();
		System.out.println(to_number);
		Random random = new Random();
		Integer otp = random.nextInt(1000000);
		// this.saveotp=otp;
		smsservice.saveotptodb(otp);
		System.out.println(otp);
		String ot = String.valueOf(otp);
		Message message = Message.creator(new PhoneNumber(to_number), new PhoneNumber(from_number), ot).create();
		System.out.println(message.getBody());
		return "HomePage";
	}

	@GetMapping("/otp")
	public String homePage1() {
		return "HomePage";
	}

	@GetMapping("/RagiFail")
	public String homePage11() {
		return "RagiFail";
	}

	@GetMapping("/RegiSuccess")
	public String homePage111() {
		return "RegiSccess";
	}

	@GetMapping("/donation")
	public String donationPage() {
		return "Donation";
	}

//   Festival
	@GetMapping("/festival")
	public String festival() {
		return "Festival";
	}

//     Gallery

	@GetMapping("/gallery")
	public String gallery() {
		return "Gallery";
	}

//    Booking 
	@GetMapping("/booking")
	public String booking() {
		return "Booking";
	}

//    Login fail
	@GetMapping("/Login")
	public String LoginPage() {
		return "HomePage";
	}

	@PostMapping("/checkvalidotp")
	public String userlogin(@RequestParam("receivedotp") Integer receivedotp) {
		System.out.println(receivedotp);

		Enteringotpmodel userdata = smsservice.userlogin(receivedotp);
		if (userdata != null) {

			return "/RegiSuccess";
		} else

			return "/RegiFail";
	}
}
