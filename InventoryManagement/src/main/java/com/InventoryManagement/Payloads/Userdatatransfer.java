package com.InventoryManagement.Payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

//Used to transfer data from entity to services;
public class Userdatatransfer {
	private int id;

	private String name;

	 private String email;
	 
	 private String password;
	 
	 private String phone;
	 
	 private String Address;
	 
	 private String about;
     
	 
	 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getAbout() {
		return about;
	}

	
	public void setAbout(String about) {
		this.about = about;
	}
}
