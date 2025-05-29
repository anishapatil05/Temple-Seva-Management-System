package com.temple.smsservice;

import java.util.Map;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.temple.entity.DonationEntity;
import com.temple.repo.DonationRepo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



@Service
public class DonationService {
	public static final String ACCOUNT_SID = "AC027a95b68aa7db50d379ab3fced2409d";
	public static final String AUTH_TOKEN = "bce28a0367bfb1d584b9159e9e34c97c";
	public static final String from_number = "+19405033983";
    public   String to_number="";
    public Integer saveotp;
    
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
    
	@Autowired
	public DonationRepo donationrepo;
	
	
	
	private RazorpayClient client;

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	
	private String keySecret;

	public DonationEntity createDonation(DonationEntity  donationentity )  throws Exception {

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", donationentity.getAmount() * 100); // amount in paise
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", String.valueOf(donationentity.getEmail()));
		System.out.println(keyId);
		System.out.println(keySecret);
		this.client = new RazorpayClient(keyId, keySecret);
		Order razorPayOrder = client.Orders.create(orderRequest);

		donationentity.setRazorPayOrderId(razorPayOrder.get("id"));
		donationentity.setOrderStatus(razorPayOrder.get("status"));

		donationrepo.save(donationentity);

		return donationentity;
	}

	public DonationEntity verifyPaymentAndUpdateOrderStatus(Map<String, String> respPayload) {
		DonationEntity donate = null;
		try {

			String razorpayOrderId = respPayload.get("razorpay_order_id");
			String razorpayPaymentId = respPayload.get("razorpay_payment_id");
			String razorpaySignature = respPayload.get("razorpay_signature");

			// Verify the signature to ensure the payload is genuine
			boolean isValidSignature = verifySignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);

			if (isValidSignature) {
				donate = donationrepo.findByRazorPayOrderId(razorpayOrderId);
				if (donate != null) {
					donate.setOrderStatus("CONFIRMED");
					donationrepo.save(donate);
					

				}
			} else {
				System.out.println("invalid");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return donate;
	}

	private boolean verifySignature(String orderId, String paymentId, String signature) throws RazorpayException {
		String generatedSignature = HmacSHA256(orderId + "|" + paymentId, keySecret);
		return generatedSignature.equals(signature);
	}
	
	private String HmacSHA256(String data, String key) throws RazorpayException {
		try {
			javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
			javax.crypto.spec.SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(key.getBytes(),
					"HmacSHA256");
			mac.init(secretKeySpec);
			byte[] hash = mac.doFinal(data.getBytes());
			return new String(org.apache.commons.codec.binary.Hex.encodeHex(hash));
		} catch (Exception e) {
			throw new RazorpayException("Failed to calculate signature.", e);
		}
	}
	
	   public void sendotp(String mnumber) {
				String to_number= mnumber;
				System.out.println( to_number);
			    	
			    	 String ot = "Dear Devotee, your donation has been received successfully. Thank you for your kind support and blessings! 🙏";
			  Message message = Message.creator(new PhoneNumber(to_number),new PhoneNumber(from_number), ot).create();
			     System.out.println(message.getBody());


			}

	

	}
	
	
	
