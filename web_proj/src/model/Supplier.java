package model;

import java.util.ArrayList;
import java.util.Date;

public class Supplier extends User {
	public ArrayList<Order> orders;

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public Supplier(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			Role role, ArrayList<Order> orders) {
		super(username, password, name, surname, gender, dateOfBirth, role);
		this.orders = orders;
	}
	
	public Supplier() {}
	
	
	
}
