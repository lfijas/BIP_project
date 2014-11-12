package com.example.bipapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mName);
		dest.writeString(mBarcode);
	}
	
}
