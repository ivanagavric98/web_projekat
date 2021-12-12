package model;

import java.util.ArrayList;

public class Order {
	public String ID;
	public ArrayList<Article> articles;
	public String restaurant;
	public String dateAndTime;
	public double price;
	public Customer customer;
	public OrderStatus orderStatus;
	
	public Order() {}

	public Order(String iD, ArrayList<Article> articles, String restaurant, String dateAndTime, double price,
			Customer customer, OrderStatus orderStatus) {
		super();
		ID = iD;
		this.articles = articles;
		this.restaurant = restaurant;
		this.dateAndTime = dateAndTime;
		this.price = price;
		this.customer = customer;
		this.orderStatus = orderStatus;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
