package org.aralmighty.dto;

public class User {
	private String firstName;
	private String lastName;
	private String address;
	private int age;
	private boolean gender;
	
	public User(String firstName, String lastName, String address, int age, boolean gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.age = age;
		this.gender = gender;
	}
	
	public boolean getGender() {
		return gender;
	}
	
	public String getGenderLable() {
		if (getGender() == false) {
			return "female";
		}else {
			return "male";
		}
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getAddressLable() {
		return (getAddress() == null ? "homeless" : getAddress());
	}
	
}