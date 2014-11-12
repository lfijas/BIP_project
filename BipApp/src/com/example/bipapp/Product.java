package com.example.bipapp;

public class Product {
	private String mName;
	private String mBarcode;
	
	public Product(String name, String barcode) {
		this.mName = name;
		this.mBarcode = barcode;				
	}
	
	public String getName() {
		return this.mName;
	}
	
	public void setName(String name) {
		this.mName = name;
	}
	
	public String getBarcode() {
		return this.mBarcode;	
	}
	
	public void setBarcode(String barcode) {
		this.mBarcode = barcode;
	}
	
	@Override
	public String toString() {
		return this.mName;
	}
	
}
