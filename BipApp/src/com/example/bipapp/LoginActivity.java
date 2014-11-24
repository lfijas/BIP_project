package com.example.bipapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private Button loginButton;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private TextView loginInfoText;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		usernameEditText = (EditText) findViewById(R.id.username_text);
		passwordEditText = (EditText) findViewById(R.id.password_text);
		loginInfoText = (TextView) findViewById(R.id.login_info_text);
		
		loginButton = (Button) findViewById(R.id.login_button);
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					String username = usernameEditText.getText().toString();
					String password = passwordEditText.getText().toString();
					if (!username.equals("\n") && !username.equals("") &&
							!password.equals("\n") && !password.equals("")) {
						new DbConnection(LoginActivity.this).execute(username, password);
					}
					else {
						Toast.makeText(LoginActivity.this,R.string.username_password_empty, Toast.LENGTH_LONG).show();
					}
				}
				else {
					Toast.makeText(LoginActivity.this,R.string.no_network, Toast.LENGTH_LONG).show();
				}	
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	private class DbConnection extends AsyncTask<String, Void, String> {
		
		private Context mContext;
		
		public DbConnection(Context context) {
			mContext = context;
		}
		
		@Override
		protected String doInBackground(String... params) {
			publishProgress();
			return connectToDatabase(params[0], params[1]);
		}
		
		@Override
		protected void onProgressUpdate(Void... params) {
			loginButton.setVisibility(View.GONE);
			usernameEditText.setVisibility(View.GONE);
			passwordEditText.setVisibility(View.GONE);
			loginInfoText.setVisibility(View.VISIBLE);
			loginInfoText.setText(R.string.please_wait);
			loginInfoText.setTextColor(Color.BLACK);
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result.equals("1")) {
				Intent intent = new Intent(mContext, BrowsePurchaseHistoryActivity.class);
				startActivity(intent);
			}
			else {
				loginInfoText.setText(R.string.login_failure);
				loginInfoText.setTextColor(Color.RED);
				loginButton.setVisibility(View.VISIBLE);
				usernameEditText.setVisibility(View.VISIBLE);
				passwordEditText.setVisibility(View.VISIBLE);
				usernameEditText.setText("");
				passwordEditText.setText("");
			}
		}
		
		public String connectToDatabase(String user, String pass) {
		    
			Connection conn = null;
			String result = "0";
			
			try {
		        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		        String username = "Admin";
		        String password = "BIP_project";
		        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://54.69.68.63:1433/BIP_project;user=" + username + ";password=" + password);
		 
		        Log.w("Connection","open");
		        Statement statement = conn.createStatement();
		        ResultSet resultSet = statement.executeQuery("SELECT user_id FROM [User]" +
		        		" WHERE username = '" + user + "' and password = '" + pass + "'");
		 

				try {
					if (resultSet != null && resultSet.next()) {
						SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
						SharedPreferences.Editor editor = settings.edit();
						editor.putInt("user_id", resultSet.getInt("user_id"));
						editor.putString("username", user);
						editor.commit();
						result = "1";
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
			return result;
		}
	}

}
