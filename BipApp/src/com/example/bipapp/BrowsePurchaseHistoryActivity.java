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
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BrowsePurchaseHistoryActivity extends Activity {

	private ListView purchaseHistoryListView;
	private ArrayAdapter<Purchase> arrayAdapter;
	private ArrayList<Purchase> mPurchaseList;
	private TextView greetingTextView;

	private OnItemClickListener mItemClickListener = null;
	
	
	public final static String EXTRA_MESSAGE = "com.example.bipapp.purchaseId";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_purchase_history);

		if (mItemClickListener == null) {
			mItemClickListener = new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					Intent intent = new Intent(BrowsePurchaseHistoryActivity.this, PurchaseSummaryActivity.class);
					String purchaseId = arrayAdapter.getItem(arg2).getId();
					intent.putExtra(EXTRA_MESSAGE, purchaseId);
					startActivity(intent);
				}
			};
		}
				
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String user_id = Integer.toString(settings.getInt("user_id", -1));
		String user = settings.getString("username", "");
		if (user.equals("")) {
			Toast.makeText(BrowsePurchaseHistoryActivity.this, R.string.error, Toast.LENGTH_LONG).show();
		}
		greetingTextView = (TextView) findViewById(R.id.greeting_text_purchase);
		greetingTextView.setText("Hi, " + user);
		
		if (savedInstanceState != null && savedInstanceState.containsKey("purchases")) {
			mPurchaseList = savedInstanceState.getParcelableArrayList("purchases");
			arrayAdapter = new ArrayAdapter<Purchase>(this, android.R.layout.simple_list_item_1, mPurchaseList);
			purchaseHistoryListView = (ListView) findViewById(R.id.purchase_history_list_view);
			purchaseHistoryListView.setAdapter(arrayAdapter);
			purchaseHistoryListView.setOnItemClickListener(mItemClickListener);
		}
		else {
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				if (!user_id.equals("-1")) {
					new DbConnection(BrowsePurchaseHistoryActivity.this).execute(user_id);
				}
				else {
					Toast.makeText(BrowsePurchaseHistoryActivity.this, R.string.error, Toast.LENGTH_LONG).show();
				}
			}
			else {
				Toast.makeText(BrowsePurchaseHistoryActivity.this, R.string.no_network, Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("purchases", mPurchaseList);
		super.onSaveInstanceState(outState);
	}
	
	private class DbConnection extends AsyncTask<String, Void, ArrayList<Purchase>> {
		
		private Context mContext;
		
		public DbConnection(Context context) {
			mContext = context;
		}
		
		@Override
		protected ArrayList<Purchase> doInBackground(String... params) {
			return connectToDatabase(params[0]);
		}
		
		@Override
		protected void onProgressUpdate(Void... params) {

		}
		
		@Override
		protected void onPostExecute(ArrayList<Purchase> result) {
			purchaseHistoryListView = (ListView) findViewById(R.id.purchase_history_list_view);
			
			mPurchaseList = result;
			arrayAdapter = new ArrayAdapter<Purchase>(mContext, android.R.layout.simple_list_item_1, mPurchaseList);
			purchaseHistoryListView.setAdapter(arrayAdapter);
			purchaseHistoryListView.setOnItemClickListener(mItemClickListener);
		}
		
		public ArrayList<Purchase> connectToDatabase(String user_id) {
		    
			Connection conn = null;
			ArrayList<Purchase> resultArrayList = null;
			
			try {
		        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		        String username = "Admin";
		        String password = "BIP_project";
		        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://54.69.68.63:1433/BIP_project;user=" + username + ";password=" + password);
		 
		        Log.w("Connection","open");
		        Statement statement = conn.createStatement();
		        ResultSet resultSet = statement.executeQuery("SELECT purchase_id, date_time FROM PurchaseHistory" +
		        		" WHERE [user_id] = " + user_id);
		 

		        resultArrayList = new ArrayList<Purchase>();
				try {
					while (resultSet != null && resultSet.next()) {
						resultArrayList.add(new Purchase(resultSet.getString("date_time"), resultSet.getString("purchase_id")));
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
