package model;

public class Article {
	public String name;
	public double price;
	public String type;
	public String restaurant;
	public int quantity;
	public String description;
	public String image;
	
	public Article () {}
	
	public Article(String name, double price, String type, String restaurant, int quantity, String description,
			String image) {
		super();
		this.name = name;
		this.price = price;
		this.type = type;
		this.restaurant = restaurant;
		this.quantity = quantity;
		this.description = description;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	
}
