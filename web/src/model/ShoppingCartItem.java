package model;

public class ShoppingCartItem {
	public String articleName;
	public int quantity;

	public ShoppingCartItem() {
	}

	public ShoppingCartItem(String article, int quantity) {
		super();
		this.articleName = article;
		this.quantity = quantity;
	}

	public String getArticle() {
		return articleName;
	}

	public void setArticle(String article) {
		this.articleName = article;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
