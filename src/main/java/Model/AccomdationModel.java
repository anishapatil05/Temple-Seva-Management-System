package com.temple;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccomdationModel {

	private Integer sn;
	private String fullname;
	private Integer age;
	private String email;
	private String mobilenumber;
	private String registerdate;
	private String gender;
	private String roomType;
}
