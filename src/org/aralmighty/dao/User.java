package org.aralmighty.dao;

import java.util.ArrayList;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private int age;
	private boolean gender;
	
	public User(int id, String firstName, String lastName, String address, int age, boolean gender) {
		this.id = id;
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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAddressLable() {
		return (getAddress() == null ? "homeless" : getAddress());
	}

	public String getActions(String contextPath, String isDetailViewable, String isEditable, String isDelettable){
		String detailViewButton =  "<a href=\""+ contextPath + "/users/"+ this.id + "\"><span class = \"glyphicon glyphicon-search\"></span></a>";
		String editButton =  "<a href=\""+ contextPath + "/users/update/"+ this.id + "\"><span class = \"glyphicon glyphicon-pencil\"></span></a>";
		String deleteButton = "<a id=\"" + this.id + "\" href=\""+ contextPath +"/users/delete/"+ this.id + "\">" +
			   						"<span class = \"glyphicon glyphicon-trash\"></span>" +
			   				  "</a>";
		String result = "";
		if (isDetailViewable.equals("true")) {
			result +=  detailViewButton;
		}
		if (isEditable.equals("true")) {
			result +=  " " + editButton;
		}
		if (isDelettable.equals("true")) {
			result += " " + deleteButton;
		}
		
		return result;
	}

	public static User[] generateUsers() {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User(1, "Ara", "Martirosyan", "rue 18", 31, true));
		users.add(new User(2, "Raymond", "Perrée", "rue 43", 30, true));
		users.add(new User(3, "Bernadette", "Clavette", "rue 15", 24, false));
		users.add(new User(4, "Adrienne", "Verllow", "rue 8", 19, false));
		users.add(new User(5, "Louis", "Deselle", "rue 19", 68, true));
		users.add(new User(6, "Nicole", "Rossiter", "rue 33", 46, false));
		users.add(new User(7, "Thomas", "Ferrande", "rue 52", 26, true));
		users.add(new User(8, "David", "Nodreau", "rue 46", 52, true));
		users.add(new User(9, "Alice", "Gaspard", "rue 22", 34, false));
		users.add(new User(10, "Roger", "Francis", "rue 11", 47, true));

		return users.toArray(new User[users.size()]);
  }
	
}