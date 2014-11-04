package com.example.bipapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new DbConnection().execute("test");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class DbConnection extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			connectToDatabase();
			return "ok";
		}
		
		public void connectToDatabase() {
		    try {
		 
		         // SET CONNECTIONSTRING
		         Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		         String username = "Admin";
		         String password = "BIP_project";
		         Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://54.69.68.63:1433/BIP_project;user=" + username + ";password=" + password);
		 
		        Log.w("Connection","open");
		        //Statement stmt = DbConn.createStatement();
		        //ResultSet reset = stmt.executeQuery(" select * from users ");
		 

		        //EditText num = (EditText) findViewById(R.id.displaymessage);
		        //num.setText(reset.getString(1));
		 
		        DbConn.close();
		 
		        } catch (Exception e) {
		        	Log.w("Error connection","" + e.getMessage());
		        	e.printStackTrace();
		        }
		}
	}
}
