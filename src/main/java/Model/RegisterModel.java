package com.temple;



import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterModel {

	private Integer sn;
	private String fullname;
	private Integer age;
	private String email;
	private String mobilenumber;
	private String registerdate;
	private String gender;
	private String timeSlot;
	
	

}
