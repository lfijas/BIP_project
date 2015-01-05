package com.example.bipapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{
    private String mName;
    private String mBarcode;
    private String mBrand;
    private String mQuantityPrice;

    public Product(String name, String barcode) {
        this.mName = name;
        this.mBarcode = barcode;
    }

    public Product(String name, String barcode, String brand) {
        this.mName = name;
        this.mBarcode = barcode;
        this.mBrand = brand;
    }

    public Product(String name, String barcode, String quantity, String price) {
        this.mName = name;
        this.mBarcode = barcode;
        this.mQuantityPrice = quantity + " x " + price;
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

    public String getBrand() {
        return this.mBrand;
    }

    public void setBrand(String brand) {
        this.mBrand = brand;
    }

    public String getQuantityPrice() {
        return this.mQuantityPrice;
    }

    public void setQuantityPrice(String quantityPrice) {
        this.mQuantityPrice = quantityPrice;
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