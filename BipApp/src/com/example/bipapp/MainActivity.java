package com.example.bipapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView searchResults;
	private ArrayAdapter<Product> arrayAdapter;
	private ArrayList<Product> mProductList;
	private TextView infoTextView;
	private Button searchButton;
	private EditText searchText;
	
	public final static String EXTRA_MESSAGE = "com.example.bipapp.message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		searchButton = (Button) findViewById(R.id.search_button);
		searchText = (EditText) findViewById(R.id.search_text);
		infoTextView = (TextView) findViewById(R.id.info_text);
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					String query = searchText.getText().toString();
					if (!query.equals("\n") && !query.equals("")) {
						searchButton.setEnabled(false);
						new DbConnection(MainActivity.this).execute(query);
					}
					else {
						infoTextView.setVisibility(View.VISIBLE);
						infoTextView.setText(R.string.empty_search);
					}
				}
				else {
					Toast.makeText(MainActivity.this,R.string.no_network, Toast.LENGTH_LONG).show();
				}
			}
		});
		searchText.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					switch (keyCode) {
					case KeyEvent.KEYCODE_ENTER:
						searchButton.performClick();
						return true;
					default:
						break;
					}
				}
				return false;
			}
		});
		
		if (savedInstanceState != null && savedInstanceState.containsKey("products")) {
			mProductList = savedInstanceState.getParcelableArrayList("products");
			arrayAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, mProductList);
			searchResults = (ListView) findViewById(R.id.search_result_list_view);
			searchResults.setAdapter(arrayAdapter);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("products", mProductList);
		super.onSaveInstanceState(outState);
	}
	
	private class DbConnection extends AsyncTask<String, Void, ArrayList<Product>> {
		
		private Context mContext;
		
		public DbConnection(Context context) {
			mContext = context;
		}
		
		@Override
		protected ArrayList<Product> doInBackground(String... params) {
			publishProgress();
			return connectToDatabase(params[0]);
		}
		
		@Override
		protected void onProgressUpdate(Void... params) {
			infoTextView.setVisibility(View.VISIBLE);
			infoTextView.setText(R.string.progress_info);
		}
		
		@Override
		protected void onPostExecute(ArrayList<Product> result) {
			//TextView infoTextView = (TextView) findViewById(R.id.info_text);
			infoTextView.setVisibility(View.GONE);
			searchButton.setEnabled(true);
			searchResults = (ListView) findViewById(R.id.search_result_list_view);
			
			mProductList = result;
			arrayAdapter = new ArrayAdapter<Product>(mContext, android.R.layout.simple_list_item_1, mProductList);
			searchResults.setAdapter(arrayAdapter);
			searchResults.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					Intent intent = new Intent(mContext, NutritionInfoActivity.class);
					String productBarcode = arrayAdapter.getItem(arg2).getBarcode();
					intent.putExtra(EXTRA_MESSAGE, productBarcode);
					startActivity(intent);
				}
			});
		}
		
		public ArrayList<Product> connectToDatabase(String productName) {
		    
			Connection conn = null;
			ArrayList<Product> resultArrayList = null;
			
			try {
		        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		        String username = "Admin";
		        String password = "BIP_project";
		        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://54.69.68.63:1433/BIP_project;user=" + username + ";password=" + password);
		 
		        Log.w("Connection","open");
		        Statement statement = conn.createStatement();
		        ResultSet resultSet = statement.executeQuery("SELECT product_name, barcode FROM Product" +
		        		" WHERE product_name like '%" + productName + "%' collate SQL_Latin1_General_CP1_CI_AI");
		 

		        resultArrayList = new ArrayList<Product>();
				try {
					while (resultSet != null && resultSet.next()) {
						resultArrayList.add(new Product(resultSet.getString("product_name").
									replaceAll("&quot;", "\""), resultSet.getString("barcode")));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 
	        	conn.close();
		 
		        } catch (Exception e) {
		        	Log.w("Error connection","" + e.getMessage());
		        	e.printStackTrace();
		        }
			return resultArrayList;
		}
	}
}
