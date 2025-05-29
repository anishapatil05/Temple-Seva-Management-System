package com.temple.repo;
import org.springframework.data.jpa.repository.*;
import com.temple.entity.*;
import java.util.List;

public interface RegisterRepo extends JpaRepository<RegisterEntity, Integer>{

	long countByRegisterdate(String registerdate);
	
	List<RegisterEntity>findByRegisterdate(String registerdate);
	
	RegisterEntity findByFullname(String fullname);
	

	
}
