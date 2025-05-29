package com.temple.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.temple.entity.DonationEntity;

// Where Integer is Primary key in the DonationEntity
public interface DonationRepo extends JpaRepository<DonationEntity, Integer> {

	public DonationEntity  findByRazorPayOrderId(String razorPayOrderId);
}
