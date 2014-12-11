package com.example.bipapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddCategoryActivity extends Activity {
	
	private EditText addCategoryEditText;
	private Button addCategoryButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_category);
		
		addCategoryEditText = (EditText) findViewById(R.id.add_category_text);
		addCategoryButton = (Button) findViewById(R.id.add_category_button);
		
		addCategoryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String category = addCategoryEditText.getText().toString();
				if (category.equals("")) {
					Toast.makeText(AddCategoryActivity.this, R.string.empty_category, Toast.LENGTH_LONG).show();
				}
				else {
					addCategoryButton.setEnabled(false);
					new DbConnection(AddCategoryActivity.this).execute(category);
				}
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_category, menu);
		return true;
	}
	
private class DbConnection extends AsyncTask<String, Void, Integer> {
		
		private Context mContext;
		
		public DbConnection(Context context) {
			mContext = context;
		}
		
		@Override
		protected Integer doInBackground(String... params) {
			return connectToDatabase(params[0]);
		}
		
		@Override
		protected void onProgressUpdate(Void... params) {

		}
		
		@Override
		protected void onPostExecute(Integer res) {
			if (res != 0){
				Toast.makeText(mContext, R.string.category_add, Toast.LENGTH_LONG).show();
			}
			finish();
		}
		
		public int connectToDatabase(String category) {
		    
			Connection conn = null;
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			int userId = settings.getInt("user_id", -1);
			int numberOfCustomCat = settings.getInt("numberOfCustomCat", -1);
			int res = 0;
			try {
		        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		        String username = "Admin";
		        String password = "BIP_project";
		        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + Utils.serverIp +":1433/BIP_project;user=" + username + ";password=" + password);
		 
		        Log.w("Connection","open");
		        Statement statement = conn.createStatement();
		        String query = "insert into CustomCategory " +
		        		"values(" + userId + ", '" + category + "')";
		        Log.i("lukasz", query);
		        res = statement.executeUpdate(query);
		        
		        
		        if (res != 0) {
			        Log.i("lukasz - num", "nr " + numberOfCustomCat);
			        SharedPreferences.Editor editor = settings.edit();
			        editor.putString("customCat_" + numberOfCustomCat, category);
			        editor.remove("numberOfCustomCat");
			        numberOfCustomCat++;
			        editor.putInt("numberOfCustomCat", numberOfCustomCat);
					editor.commit();
		        }
		 
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
