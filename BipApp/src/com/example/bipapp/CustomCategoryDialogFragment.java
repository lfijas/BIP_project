package com.example.bipapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CustomCategoryDialogFragment extends DialogFragment {
	private ListView searchResults;
	private ArrayAdapter<String> mArrayAdapter;
	private ArrayList<String> mCustomCategoryList;
	private ArrayList<Integer> mSelectedItems;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mCustomCategoryList = new ArrayList<String>();
		int size = sp.getInt("numberOfCustomCat", 0);
		for (int i = 0; i < size; i++) {
			mCustomCategoryList.add(sp.getString("customCat_" + i, ""));
		}
		CharSequence[] charSeqOfNames = mCustomCategoryList.toArray(new CharSequence[mCustomCategoryList.size()]);
		mSelectedItems = new ArrayList<Integer>();
		builder.setTitle("Assign custom category")
			.setMultiChoiceItems(charSeqOfNames, null, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					if (isChecked) {
						mSelectedItems.add(which);
					} else if (mSelectedItems.contains(which)) {
						mSelectedItems.remove(which);
					}
					
				}
			})
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});	
		return builder.create();
	}
}