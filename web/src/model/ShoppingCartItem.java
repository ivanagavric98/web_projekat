package model;

public class ShoppingCartItem {
	public String articleName;
	public int quantity;
	public String image;
	public double price;

	public ShoppingCartItem() {
	}

	public ShoppingCartItem(String articleName, int quantity, String image, double price) {
		super();
		this.articleName = articleName;
		this.quantity = quantity;
		this.image = image;
		this.price = price;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	}
