package model;

import java.util.ArrayList;

public class ShoppingCart {
	public int id;
	public ArrayList<ShoppingCartItem> items;
	public String customer;
	public double price;
	public String restaurantName;

	public ShoppingCart() {
	}

	public ArrayList<ShoppingCartItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<ShoppingCartItem> items) {
		this.items = items;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public ShoppingCart(int id, ArrayList<ShoppingCartItem> items, String customer, double price,
			String restaurantName) {
		super();
		this.id = id;
		this.items = items;
		this.customer = customer;
		this.price = price;
		this.restaurantName = restaurantName;
	}

}
