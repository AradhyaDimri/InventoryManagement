package com.InventoryManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.InventoryManagement.entities.User;


	@Repository
	public interface UserRepo extends JpaRepository<User, Long> {
	    User findByEmail(String email);
	   
		User findByEmailAndVerificationToken(String email, String token);

		Optional<User> findById(Integer id);
		
	}
	  
	
