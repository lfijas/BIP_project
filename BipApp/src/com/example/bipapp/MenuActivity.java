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

public class MenuActivity extends Activity {
	
	private Button browseProductCatalogButton;
	private Button browseProductHistoryButton;
	private Button logoutButton;
	private TextView greetingText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		browseProductCatalogButton = (Button) findViewById(R.id.browseProductCatalog);
		browseProductHistoryButton = (Button) findViewById(R.id.browsePurchaseHistory);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		greetingText = (TextView) findViewById(R.id.greeting_text);
		
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
				Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
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
			greetingText.setText(R.string.greeting + username);
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
