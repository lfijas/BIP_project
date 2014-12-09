package com.example.bipapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NutritionInfoActivity extends Activity {
	
	private TextView mName;
	private TextView mCalories;
	private TextView mSugar;
	private TextView mCarbohydrates;
	private TextView mProteins;
	private TextView mFat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nutrition_info);
		
		mName = (TextView) findViewById(R.id.product_name_text);
		mCalories = (TextView) findViewById(R.id.calories_text);
		mSugar = (TextView) findViewById(R.id.sugar_text);
		mCarbohydrates = (TextView) findViewById(R.id.carbohydrates_text);
		mProteins = (TextView) findViewById(R.id.proteins_text);
		mFat = (TextView) findViewById(R.id.fat_text);
		
		if (savedInstanceState != null) {
			mName.setText(savedInstanceState.getString("product"));
			mCalories.setText(savedInstanceState.getString("calories"));
			mSugar.setText(savedInstanceState.getString("sugar"));
			mCarbohydrates.setText(savedInstanceState.getString("carbohydrates"));
			mProteins.setText(savedInstanceState.getString("proteins"));
			mFat.setText(savedInstanceState.getString("fat"));
		}
		else {
			Intent intent = getIntent();
			String productBarcode = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
			
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				new DbConnection(NutritionInfoActivity.this).execute(productBarcode);
				}
			else {
				Toast.makeText(NutritionInfoActivity.this,R.string.no_network, Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nutrition_info, menu);
		return true;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString("product", mName.getText().toString());
		outState.putString("calories", mCalories.getText().toString());
		outState.putString("sugar", mSugar.getText().toString());
		outState.putString("carbohydrates", mCarbohydrates.getText().toString());
		outState.putString("proteins", mProteins.getText().toString());
		outState.putString("fat", mFat.getText().toString());
		super.onSaveInstanceState(outState);
	}
	
	private class DbConnection extends AsyncTask<String, Void, Hashtable<String, String>> {
		
		private Context mContext;
		
		public DbConnection(Context context) {
			mContext = context;
		}
		
		@Override
		protected Hashtable<String, String> doInBackground(String... params) {
			publishProgress();
			return connectToDatabase(params[0]);
		}
		
		@Override
		protected void onProgressUpdate(Void... params) {
			//infoTextView.setVisibility(View.VISIBLE);
			//infoTextView.setText(R.string.progress_info);
		}
		
		@Override
		protected void onPostExecute(Hashtable<String, String> hs) {
			String temp;
			TextView name = (TextView) findViewById(R.id.product_name_text);
			name.setText(hs.get("name"));
			temp = hs.get("calories");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.calories_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.calories_text);
				calories.setText(temp);
			}
			temp = hs.get("sugar");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.sugar_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.sugar_text);
				calories.setText(temp);
			}
			temp = hs.get("carbohydrates");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.carbohydrates_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.carbohydrates_text);
				calories.setText(temp);
			}
			temp = hs.get("proteins");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.proteins_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.proteins_text);
				calories.setText(temp);
			}
			temp = hs.get("fat");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.fat_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.fat_text);
				calories.setText(temp);
			}
			temp = hs.get("fat");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.fat_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.fat_text);
				calories.setText(temp);
			}
			temp = hs.get("saturated_fat");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.saturated_fat_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.saturated_fat_text);
				calories.setText(temp);
			}
			temp = hs.get("fiber");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.fiber_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.fiber_text);
				calories.setText(temp);
			}
			temp = hs.get("cholesterol");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.cholesterol_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.cholesterol_text);
				calories.setText(temp);
			}
			temp = hs.get("sodium");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.sodium_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.sodium_text);
				calories.setText(temp);
			}
			temp = hs.get("calcium");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.calcium_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.calcium_text);
				calories.setText(temp);
			}
			temp = hs.get("iron");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.iron_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.iron_text);
				calories.setText(temp);
			}
			temp = hs.get("vitA");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.vitamin_a_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.vitamin_a_text);
				calories.setText(temp);
			}
			temp = hs.get("vitC");
			if (!temp.equals("")) {
				((LinearLayout) findViewById(R.id.vitamin_c_layout)).setVisibility(View.VISIBLE);
				TextView calories = (TextView) findViewById(R.id.vitamin_c_text);
				calories.setText(temp);
			}
		}
		
		private String isNull(String s) {
			if (s == null)
				return "";
			return s;
		}
		
		public Hashtable<String, String> connectToDatabase(String productBarcode) {
		    
			Connection conn = null;
			ArrayList<String> resultArrayList = null;
			ResultSet resultSet = null;
			Hashtable<String, String> nutritionalHash = new Hashtable<String, String>(); 
			
			try {
		        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		        String username = "Admin";
		        String password = "BIP_project";
		        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + Utils.serverIp + ":1433/BIP_project;user=" + username + ";password=" + password);
		 
		        Log.w("Connection","open");
		        Statement statement = conn.createStatement();
		        resultSet = statement.executeQuery("SELECT * FROM Product" +
		        		" WHERE barcode = " + productBarcode);
		        if (resultSet!=null && resultSet.next()) {
		        	String calories = isNull(resultSet.getString("calories"));
		        	nutritionalHash.put("calories", calories);
		        	String sugar = isNull(resultSet.getString("sugar_100"));
		        	nutritionalHash.put("sugar", sugar);
		        	String carbohydrates = isNull(resultSet.getString("carbohydrates_100"));
		        	nutritionalHash.put("carbohydrates", carbohydrates);
		        	String proteins = isNull(resultSet.getString("proteins_100"));
		        	nutritionalHash.put("proteins", proteins);
		        	String fat = isNull(resultSet.getString("fat_100"));
		        	nutritionalHash.put("fat", fat);
		        	String saturatedFat = isNull(resultSet.getString("saturated_fat_100"));
		        	nutritionalHash.put("saturated_fat", saturatedFat);
		        	String cholesterol = isNull(resultSet.getString("cholesterol_100"));
		        	nutritionalHash.put("cholesterol", cholesterol);
		        	String sodium = isNull(resultSet.getString("sodium_100"));
		        	nutritionalHash.put("sodium", sodium);
		        	String calcium = isNull(resultSet.getString("calcium_100"));
		        	nutritionalHash.put("calcium", calcium);
		        	String iron = isNull(resultSet.getString("iron_100"));
		        	nutritionalHash.put("iron", iron);
		        	String vitA = isNull(resultSet.getString("vitamin_a"));
		        	nutritionalHash.put("vitA", vitA);
		        	String vitC = isNull(resultSet.getString("vitamin_c"));
		        	nutritionalHash.put("vitC", vitC);
		        	String fiber = isNull(resultSet.getString("fiber_100"));
		        	nutritionalHash.put("fiber", fiber);
		        	String name = isNull(resultSet.getString("product_name"));
		        	nutritionalHash.put("name", name.replaceAll("&quot;", "\""));
		        }
	        	conn.close();
		 
		        } catch (Exception e) {
		        	Log.w("Error connection","" + e.getMessage());
		        	e.printStackTrace();
		        }
			return nutritionalHash;
		}
	}


}
