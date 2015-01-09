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
	private int mCustomCategoryListSize;
	private boolean[] mAddedCategories;
	private boolean[] mRemovedCategories;
	private boolean[] mChangedCategories;
	private Context mContext;
	private String mBarcode;
	private String mUserId;
	
	public static CustomCategoryDialogFragment newInstance(String[] assignedCategories,
			String barcode, String userID) {
        CustomCategoryDialogFragment frag = new CustomCategoryDialogFragment();
        Bundle args = new Bundle();
        args.putStringArray("assigned_categories", assignedCategories);
        args.putString("barcode", barcode);
        args.putString("user_id", userID);
        frag.setArguments(args);
        return frag;
    }

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Log.i("lukasz", "onCreateDialog");
		String[] assignedCategories = getArguments().getStringArray("assigned_categories");
		mBarcode = getArguments().getString("barcode");
		mUserId = getArguments().getString("user_id");
		
		mContext = getActivity();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mCustomCategoryListSize = sp.getInt("numberOfCustomCat", 0);
		
		
		mCustomCategoryList = new ArrayList<String>();
		
		boolean[] checked = new boolean[mCustomCategoryListSize];
		mAddedCategories = new boolean[mCustomCategoryListSize];
		mRemovedCategories = new boolean[mCustomCategoryListSize];
		mChangedCategories = new boolean[mCustomCategoryListSize];
		
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
		builder.setTitle("Assign custom category")
			.setMultiChoiceItems(charSeqOfNames, checked, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					if (isChecked) {
						mAddedCategories[which] = true;
						mRemovedCategories[which] = false;
						mChangedCategories[which] = !mChangedCategories[which];
					} else {
						mAddedCategories[which] = false;
						mRemovedCategories[which] = true;
						mChangedCategories[which] = !mChangedCategories[which];
					}
					
				}
			})
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					for (int i = 0; i < mCustomCategoryListSize; i++) {
						Log.i("lukasz", "petla");
						if (mChangedCategories[i] && mAddedCategories[i]) {
							Log.i("lukasz", "pierwszy if");
							new DbConnection(mContext).execute(mCustomCategoryList.get(i), mBarcode, mUserId, "add");
						}
						else if (mChangedCategories[i] && mRemovedCategories[i]) {
							Log.i("lukasz", "else if");
							new DbConnection(mContext).execute(mCustomCategoryList.get(i), mBarcode, mUserId ,"remove");
						}
					}
					
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
	
	private class DbConnection extends AsyncTask<String, Void, Integer> {
		
		private Context mContext;
		
		public DbConnection(Context context) {
			mContext = context;
		}
		
		@Override
		protected Integer doInBackground(String... params) {
			return connectToDatabase(params[0], params[1], params[2], params[3]);
		}
		
		@Override
		protected void onProgressUpdate(Void... params) {

		}
		
		@Override
		protected void onPostExecute(Integer res) {
			if (res != 0){
				Log.i("lukasz", "Number of affected rows: " + res);
			}
		}
		
		public int connectToDatabase(String category, String barcode, String userId, String action) {
		    
			Connection conn = null;
			int res = 0;
			try {
		        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		        String username = "Admin";
		        String password = "BIP_project";
		        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + Utils.serverIp +":1433/BIP_project;user=" + username + ";password=" + password);
		 
		        Log.w("Connection","open");
		        Statement statement = conn.createStatement();
		        String query;
		        if (action.equals("add")) {
		        	query = "insert into Product_CustomCategory "
		        			+ "values ("
		        					+ "(SELECT [custom_cat_id] "
		        					+ "FROM [CustomCategory] "
		        					+ "where [user_id] = " + userId
		        					+ " and custom_category_name = '" + category + "'), " + barcode +")";
		        	Log.i("lukasz", query);
		        }
		        else {
		        	query = "delete from Product_CustomCategory "
		        			+ "where barcode = " + barcode + " and custom_cat_id = ("
		        					+ "SELECT [custom_cat_id] "
		        					+ "FROM [CustomCategory] "
		        					+ "where [user_id] = " + userId
		        					+ " and custom_category_name = '" + category + "')";
		        	Log.i("lukasz", query);
		        }
		        res = statement.executeUpdate(query);
		 
	        	conn.close();
	        	Log.i("lukasz", "doszlo");
		        } catch (Exception e) {
		        	Log.w("Error connection","" + e.getMessage());
		        	e.printStackTrace();
		        }
			return res;
		}
	}
	
}