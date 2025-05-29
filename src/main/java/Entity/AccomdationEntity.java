package com.temple.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor

@Entity
public class AccomdationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer sn;
	private String fullname;
	private Integer age;
	private String email;
	private String mobilenumber;

	private String registerdate;
	private String gender;
	private String roomType;
}
