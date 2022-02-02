package model;

import java.util.ArrayList;
import java.util.Date;

public class Customer extends User {
	public ArrayList<Order> orders;
	public ShoppingCart cart;
	public Double points;
	public CustomerType type;

	
	
	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}

	public Customer(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			Role role, ArrayList<Order> orders, ShoppingCart cart, Double points, CustomerType type,  Boolean deleted) {
		super(username, password, name, surname, gender, dateOfBirth, role, deleted);
		this.orders = orders;
		this.cart = cart;
		this.points = points;
		this.type = type;
	}

	public Customer(User user) {
		this.username = user.username;
		this.password = user.password;
		this.name = user.name;
		this.surname = user.surname;
		this.gender = user.gender;
		this.dateOfBirth = user.dateOfBirth;
		this.role = user.role;
		this.points = 0.0;
		this.type = new CustomerType();
		this.cart = new ShoppingCart();
		this.orders = new ArrayList<Order>();

	}

	public Customer() {
	}

}
