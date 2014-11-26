package com.example.bipapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Purchase implements Parcelable{
	private String mDate;
	private String mId;
	private String mBranch;
	
	public Purchase(String date, String id) {
		this.mDate = date;
		this.mId = id;				
	}
	
	public Purchase(String date, String id, String branch) {
		this.mDate = date;
		this.mId = id;
		this.mBranch = branch;
	}
	
	public String getDate() {
		return this.mDate;
	}
	
	public void setDate(String date) {
		this.mDate = date;
	}
	
	public String getId() {
		return this.mId;	
	}
	
	public void setId(String id) {
		this.mId = id;
	}
	
	public String getBranch() {
		return this.mBranch;
	}
	
	public void setBranch(String branch) {
		this.mBranch = branch;
	}
	
	@Override
	public String toString() {
		return this.mDate;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mDate);
		dest.writeString(mId);
	}
	
}

