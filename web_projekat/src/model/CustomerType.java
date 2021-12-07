package model;

public class CustomerType {
	public Type type;
	public int discount;
	public int requiredPoints;
	public CustomerType(Type type, int discount, int requiredPoints) {
		super();
		this.type = type;
		this.discount = discount;
		this.requiredPoints = requiredPoints;
	}
	
	public CustomerType() {}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getRequiredPoints() {
		return requiredPoints;
	}

	public void setRequiredPoints(int requiredPoints) {
		this.requiredPoints = requiredPoints;
	}
	
	
}
