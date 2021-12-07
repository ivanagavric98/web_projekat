package model;

import java.util.Date;

public class Menager extends User {
	public String restaurant;
	public Menager() {}
	public Menager(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			Role role, String restaurant) {
		super(username, password, name, surname, gender, dateOfBirth, role);
		this.restaurant = restaurant;
	}
	public String getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
	
}
