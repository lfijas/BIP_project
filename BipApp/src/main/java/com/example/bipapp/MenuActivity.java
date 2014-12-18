package com.example.bipapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {
	
	private Button browseProductCatalogButton;
	private Button browseProductHistoryButton;
	private Button logoutButton;
	private Button addCustomCategoryButton;
	private TextView greetingText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		browseProductCatalogButton = (Button) findViewById(R.id.browseProductCatalog);
		browseProductHistoryButton = (Button) findViewById(R.id.browsePurchaseHistory);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		greetingText = (TextView) findViewById(R.id.greeting_text);
		addCustomCategoryButton = (Button) findViewById(R.id.add_category_button);
		
		browseProductCatalogButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		
		browseProductHistoryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				if (!settings.getString("username", "").equals("")) {
					Intent intent = new Intent(MenuActivity.this, BrowsePurchaseHistoryActivity.class);
					startActivity(intent);
				}
				else {
					Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
					startActivity(intent);
				}
			}
		});
		
		logoutButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				int numberOfCustomCat = settings.getInt("numberOfCustomCat", -1);
				SharedPreferences.Editor editor = settings.edit();
				editor.remove("username");
				editor.remove("user_id");
				for (int i = 0; i < numberOfCustomCat; i++) {
					editor.remove("customCat_" + i);
				}
				editor.remove("numberOfCustomCat");
				editor.commit();
				logoutButton.setVisibility(View.GONE);
				greetingText.setVisibility(View.GONE);
				addCustomCategoryButton.setVisibility(View.GONE);
				Toast.makeText(MenuActivity.this, R.string.logout_info, Toast.LENGTH_LONG).show();
			}
		});
		
		addCustomCategoryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this, AddCategoryActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MenuActivity.this);
		String username = settings.getString("username", "");
		if (!username.equals("")) {
			logoutButton.setVisibility(View.VISIBLE);
			addCustomCategoryButton.setVisibility(View.VISIBLE);
			greetingText.setText("Hi, " + username);
			greetingText.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
