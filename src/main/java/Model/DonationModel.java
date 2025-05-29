package com.temple;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationModel {

	private Integer orderId;
	private String name;
	private String email;
	private String phno;
	private Integer amount;
	private String orderStatus;
	private String razorPayOrderId;
}
