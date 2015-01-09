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
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CustomCategoryDialogFragment extends DialogFragment {
	private ListView searchResults;
	private ArrayAdapter<String> mArrayAdapter;
	private ArrayList<String> mCustomCategoryList;
	private ArrayList<Integer> mSelectedItems;
	private int mCustomCategoryListSize;
	private boolean[] mAddedCategories;
	private boolean[] mRemovedCategories;
	
	public static CustomCategoryDialogFragment newInstance(String[] assignedCategories) {
        CustomCategoryDialogFragment frag = new CustomCategoryDialogFragment();
        Bundle args = new Bundle();
        args.putStringArray("assigned_categories", assignedCategories);
        frag.setArguments(args);
        return frag;
    }

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String[] assignedCategories = getArguments().getStringArray("assigned_categories");
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mCustomCategoryListSize = sp.getInt("numberOfCustomCat", 0);
		
		
		mCustomCategoryList = new ArrayList<String>();
		
		boolean[] checked = new boolean[mCustomCategoryListSize];
		mAddedCategories = new boolean[mCustomCategoryListSize];
		mRemovedCategories = new boolean[mCustomCategoryListSize];
		
		for (int i = 0; i < mCustomCategoryListSize; i++) {
			String cat = sp.getString("customCat_" + i, "");
			mCustomCategoryList.add(cat);
			for (int j = 0; j < assignedCategories.length; j++) {
				if (cat.equals(assignedCategories[j])) {
					checked[i] = true;
					break;
				}
			}
		}
		CharSequence[] charSeqOfNames = mCustomCategoryList.toArray(new CharSequence[mCustomCategoryList.size()]);
		mSelectedItems = new ArrayList<Integer>();
		builder.setTitle("Assign custom category")
			.setMultiChoiceItems(charSeqOfNames, checked, new DialogInterface.OnMultiChoiceClickListener() {
				
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