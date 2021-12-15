package model;

import java.util.ArrayList;

public class Restaurant {
	public String name;
	public String type;
	public ArrayList<Article> articles;
	public Location location;
	public String logo;
	public double grade;
	public RestaurantStatus status;
	
	public Restaurant() {}
		
	public Restaurant(String name, String type, ArrayList<Article> articles, Location location, String logo,Double grade,RestaurantStatus status) {
		super();
		this.name = name;
		this.type = type;
		this.articles = articles;
		this.location = location;
		this.logo = logo;
		this.grade = grade;
		this.status=status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public RestaurantStatus getStatus() {
		return status;
	}

	public void setStatus(RestaurantStatus status) {
		this.status = status;
	}
	
	
	

}
