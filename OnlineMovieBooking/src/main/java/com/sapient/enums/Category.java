package com.sapient.enums;

public enum Category{
	
	PLATINUM (300), GOLD (200), SILVER (150);

	Category(int amount) {
		this.amount = amount;
	} 
	
	private int amount;
	
	public int getAmount() {
		return amount;
	}

}