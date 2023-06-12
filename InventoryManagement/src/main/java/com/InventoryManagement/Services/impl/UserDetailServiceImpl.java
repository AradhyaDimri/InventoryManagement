package com.InventoryManagement.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.InventoryManagement.entities.User;
import com.InventoryManagement.repository.UserRepo;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
   private UserRepo userRepo;

     public UserDetailServiceImpl(UserRepo userRepo) {
	
	   this.userRepo = userRepo;
     }

    @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	  User user = userRepo.findByEmail(username);

          if (user == null) {
              throw new UsernameNotFoundException("User not found");
          }

          
          return org.springframework.security.core.userdetails.User
        		  .withUsername(user.getEmail())
                  .password(user.getPassword())
                  .roles("USER")
                  .build();
    }

	
	}
    
	


