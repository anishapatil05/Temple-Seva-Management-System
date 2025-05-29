package com.temple;

public class Enteringotpmodel {

	private Integer receivedotp;

	public Enteringotpmodel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Enteringotpmodel(Integer receivedotp) {
		super();
		this.receivedotp = receivedotp;
	}

	public Integer getReceivedotp() {
		return receivedotp;
	}

	public void setReceivedotp(Integer receivedotp) {
		this.receivedotp = receivedotp;
	}

	@Override
	public String toString() {
		return "Enteringotpmodel [receivedotp=" + receivedotp + "]";
	}
	
	

	
	
}
