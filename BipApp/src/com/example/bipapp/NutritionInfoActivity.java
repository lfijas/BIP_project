package com.example.bipapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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
			TextView name = (TextView) findViewById(R.id.product_name_text);
			name.setText(hs.get("name"));
			TextView calories = (TextView) findViewById(R.id.calories_text);
			calories.setText(hs.get("calories"));
			TextView sugar = (TextView) findViewById(R.id.sugar_text);
			sugar.setText(hs.get("sugar"));
			TextView carbohydrates = (TextView) findViewById(R.id.carbohydrates_text);
			carbohydrates.setText(hs.get("carbohydrates"));
			TextView proteins = (TextView) findViewById(R.id.proteins_text);
			proteins.setText(hs.get("proteins"));
			TextView fat = (TextView) findViewById(R.id.fat_text);
			fat.setText(hs.get("fat"));
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
		        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://54.69.68.63:1433/BIP_project;user=" + username + ";password=" + password);
		 
		        Log.w("Connection","open");
		        Statement statement = conn.createStatement();
		        resultSet = statement.executeQuery("SELECT product_name, fat_100, carbohydrates_100, sugar_100, calories, proteins_100  FROM Product" +
		        		" WHERE barcode = " + productBarcode);
		        if (resultSet!=null && resultSet.next()) {
		        	String calories = resultSet.getString("calories");
		        	if (calories == null) {
		        		calories = "";
		        	}
		        	nutritionalHash.put("calories", calories);
		        	String sugar = resultSet.getString("sugar_100");
		        	if (sugar== null) {
		        		sugar = "";
		        	}
		        	nutritionalHash.put("sugar", sugar);
		        	String carbohydrates = resultSet.getString("carbohydrates_100");
		        	if (carbohydrates == null) {
		        		carbohydrates = "";
		        	}
		        	nutritionalHash.put("carbohydrates", carbohydrates);
		        	String proteins = resultSet.getString("proteins_100");
		        	if (proteins == null) {
		        		proteins = "";
		        	}
		        	nutritionalHash.put("proteins", proteins);
		        	String fat = resultSet.getString("fat_100");
		        	if (fat == null) {
		        		fat = "";
		        	}
		        	nutritionalHash.put("fat", fat);
		        	String name = resultSet.getString("product_name");
		        	if (name == null) {
		        		name = "";
		        	}
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
