package com.temple.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SmsEntity {
	  @Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer sn;
		private Integer otp;
		public Integer getSn() {
			return sn;
		}
		public void setSn(Integer sn) {
			this.sn = sn;
		}
		public Integer getOtp() {
			return otp;
		}
		public void setOtp(Integer otp) {
			this.otp = otp;
		}
		
		

}
