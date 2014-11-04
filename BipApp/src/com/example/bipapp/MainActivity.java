package com.example.bipapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView searchResults;
	private ArrayAdapter arrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button searchButton = (Button) findViewById(R.id.search_button);
		final EditText searchText = (EditText) findViewById(R.id.search_text);
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new DbConnection(MainActivity.this).execute((String)searchText.getText().toString());
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class DbConnection extends AsyncTask<String, Void, ArrayList<String>> {
		
		private Context mContext;
		
		public DbConnection(Context context) {
			mContext = context;
		}
		
		@Override
		protected ArrayList<String> doInBackground(String... params) {
			return connectToDatabase(params[0]);
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			searchResults = (ListView) findViewById(R.id.search_result_list_view);
			
			String[] testArray = {"one", "two"};
			arrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, result);
			searchResults.setAdapter(arrayAdapter);
		}
		
		public ArrayList<String> connectToDatabase(String productName) {
		    
			Connection conn = null;
			ArrayList<String> resultArrayList = null;
			
			try {
		        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		        String username = "Admin";
		        String password = "BIP_project";
		        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://54.69.68.63:1433/BIP_project;user=" + username + ";password=" + password);
		 
		        Log.w("Connection","open");
		        Statement statement = conn.createStatement();
		        ResultSet resultSet = statement.executeQuery("SELECT product_name  FROM Product" +
		        		" WHERE product_name like '%" + productName + "%' collate SQL_Latin1_General_CP1_CI_AI");
		 

		        resultArrayList = new ArrayList<String>();
				try {
					while (resultSet != null && resultSet.next()) {
						resultArrayList.add(resultSet.getString("product_name"));
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
