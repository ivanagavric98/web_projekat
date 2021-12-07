package model;

import java.util.ArrayList;
import java.util.Date;

public class Customer extends User {
	public ArrayList<Order> orders;
	public ShoppingCart cart;
	public int points;
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
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public CustomerType getType() {
		return type;
	}
	public void setType(CustomerType type) {
		this.type = type;
	}
	public Customer(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			Role role, ArrayList<Order> orders, ShoppingCart cart, int points, CustomerType type) {
		super(username, password, name, surname, gender, dateOfBirth, role);
		this.orders = orders;
		this.cart = cart;
		this.points = points;
		this.type = type;
	}
	
	public Customer() {}

}
