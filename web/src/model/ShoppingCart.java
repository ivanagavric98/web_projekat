package model;

import java.util.ArrayList;

public class ShoppingCart {
	public ArrayList<ShoppingCartItem> items;
	public Customer customer;
	public double price;
	
	public ShoppingCart () {}

	public ArrayList<ShoppingCartItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<ShoppingCartItem> items) {
		this.items = items;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ShoppingCart(ArrayList<ShoppingCartItem> items, Customer customer, double price) {
		super();
		this.items = items;
		this.customer = customer;
		this.price = price;
	}
	
	
	
	
}
