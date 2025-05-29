package com.temple.smsservice;

import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.temple.entity.PaymentEntity;
import com.temple.repo.PaymentRepo;


@Service
public class PaymentService {

	@Autowired
	public PaymentRepo paymentrepo;

	private RazorpayClient client;

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")

	private String keySecret;

	public PaymentEntity createPayment(PaymentEntity paymentEntity) throws Exception {

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", paymentEntity.getAmount() * 100); // amount in paise
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", String.valueOf(paymentEntity.getEmail()));
		//System.out.println(keyId);
		//System.out.println(keySecret);
		this.client = new RazorpayClient(keyId, keySecret);
		Order razorPayOrder = client.Orders.create(orderRequest);

		paymentEntity.setRazorPayOrderId(razorPayOrder.get("id"));
		paymentEntity.setOrderStatus(razorPayOrder.get("status"));

		paymentrepo.save(paymentEntity);

		return paymentEntity;
	}

	public PaymentEntity verifyPaymentAndUpdateOrderStatus(Map<String, String> respPayload) {
		PaymentEntity payment = null;
		try {

			String razorpayOrderId = respPayload.get("razorpay_order_id");
			String razorpayPaymentId = respPayload.get("razorpay_payment_id");
			String razorpaySignature = respPayload.get("razorpay_signature");

			// Verify the signature to ensure the payload is genuine
			boolean isValidSignature = verifySignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);

			if (isValidSignature) {
				payment = paymentrepo.findByRazorPayOrderId(razorpayOrderId);
				if (payment != null) {
					payment.setOrderStatus("CONFIRMED");
					paymentrepo.save(payment);
				}
			} else {
				System.out.println("invalid");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return payment;
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

}
