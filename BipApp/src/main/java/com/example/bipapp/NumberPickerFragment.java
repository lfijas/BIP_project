package com.example.bipapp;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

public class NumberPickerFragment /*extends DialogFragment
					implements NumberPickerDialog.*/ {
	
	/*int mNum;
	
	static DatePickerFragment newInstance(int num) {
	DatePickerFragment f = new DatePickerFragment();
	Bundle args = new Bundle();
	args.putInt("num", num);
	f.setArguments(args);
	return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	mNum = getArguments().getInt("num");
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	final Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);
	
	//return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	
	@Override
	public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
	if (mNum == 0) {
	Log.i("lukasz", "lukasz");
	}
	else {
	Log.i("mama", "mama");
	}
	
	}*/
}
