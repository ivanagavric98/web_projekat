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
			Role role, ArrayList<Order> orders,  Boolean deleted) {
		super(username, password, name, surname, gender, dateOfBirth, role, deleted);
		this.orders = orders;
	}

	
	public Supplier(User user){
		this.username = user.username;
		this.password = user.password;
		this.name = user.name;
		this.surname = user.surname;
		this.gender = user.gender;
		this.dateOfBirth = user.dateOfBirth;
		this.role = user.role;
		this.orders = new ArrayList<Order>();
		
	}
	
	public Supplier() {}
	
	
	
}
