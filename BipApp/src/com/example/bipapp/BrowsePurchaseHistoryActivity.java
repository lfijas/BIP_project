package com.example.bipapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BrowsePurchaseHistoryActivity extends Activity {

	private ListView purchaseHistoryListView;
	private ArrayAdapter<Purchase> arrayAdapter;
	private ArrayList<Purchase> mPurchaseList;
	private TextView greetingTextView;

	private OnItemClickListener mItemClickListener = null;
	
	private Button mViewPurchaseHistorySummaryButton;
	
	private Context mContext;
	private String userId;
	
	private String mDateFrom;
	private String mDateTill;
	
	public final static String EXTRA_MESSAGE = "com.example.bipapp.purchaseId";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_purchase_history);
		mContext = this;

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
		
		SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.fliters, R.layout.spinner_item);
		OnNavigationListener callback = new OnNavigationListener() {
			
			String[] items = getResources().getStringArray(R.array.fliters);
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				DialogFragment datePicker;
				switch(itemPosition) {
					case 0:
						break;
					case 1:
						DateRangeDialogFragment dateDialog = new DateRangeDialogFragment();
						dateDialog.show(((Activity) mContext).getFragmentManager(), "date_range_dialog");
						((Activity) mContext).getActionBar().setSelectedNavigationItem(0);
						break;
					/*case 2:
						PriceRangeDialogFragment priceDialog = new PriceRangeDialogFragment();
						priceDialog.show(((Activity) mContext).getFragmentManager(), "price_range_dialog");
						((Activity) mContext).getActionBar().setSelectedNavigationItem(0);
						break;*/
					case 2:
						applyFilter(getUserId(), null, null);
						((Activity) mContext).getActionBar().setSelectedNavigationItem(0);
						break;
				}
				return false;
			}
		};
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setListNavigationCallbacks(spinnerAdapter, callback);
		
		mViewPurchaseHistorySummaryButton = (Button) findViewById(R.id.view_purchase_history_button);
		mViewPurchaseHistorySummaryButton.setOnClickListener(new View.OnClickListener()  {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BrowsePurchaseHistoryActivity.this, FilterSummaryActivity.class);
				if (mDateFrom == null || mDateTill == null) {
					intent.putExtra("date_from", "");
					intent.putExtra("date_till", "");
				}
				else {
					intent.putExtra("date_from", mDateFrom);
					intent.putExtra("date_till", mDateTill);
				}
				startActivity(intent);
			}
		});
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		userId = Integer.toString(settings.getInt("user_id", -1));
		String user = settings.getString("username", "");
		if (user.equals("")) {
			Toast.makeText(BrowsePurchaseHistoryActivity.this, R.string.error, Toast.LENGTH_LONG).show();
		}
		greetingTextView = (TextView) findViewById(R.id.greeting_text_purchase);
		greetingTextView.setText("Hi, " + user);
		
		if (savedInstanceState != null && savedInstanceState.containsKey("purchases")) {
			mPurchaseList = savedInstanceState.getParcelableArrayList("purchases");
			arrayAdapter = new ArrayAdapter<Purchase>(this, android.R.layout.simple_list_item_2, android.R.id.text1, mPurchaseList){
			    @Override
				public View getView(int position, View convertView, ViewGroup parent) {
			        View view = super.getView(position, convertView, parent);
			        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
			        text1.setText(mPurchaseList.get(position).getDate());
			        text2.setText(mPurchaseList.get(position).getBranch());
			        return view;
			      }
			    };
			purchaseHistoryListView = (ListView) findViewById(R.id.purchase_history_list_view);
			purchaseHistoryListView.setAdapter(arrayAdapter);
			purchaseHistoryListView.setOnItemClickListener(mItemClickListener);
		}
		else {
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				if (!userId.equals("-1")) {
					new DbConnection(BrowsePurchaseHistoryActivity.this).execute(userId, null, null);
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
	
	public String getUserId() {
		return userId;
	}
	
	public void applyFilter(String user_id, String dateFrom, String dateTill) {
		mDateFrom = dateFrom;
		mDateTill = dateTill;
		new DbConnection(BrowsePurchaseHistoryActivity.this).execute(user_id, dateFrom, dateTill);
	}
	
	private class DbConnection extends AsyncTask<String, Void, ArrayList<Purchase>> {
		
		private Context mContext;
		
		public DbConnection(Context context) {
			mContext = context;
		}
		
		@Override
		protected ArrayList<Purchase> doInBackground(String... params) {
			return connectToDatabase(params[0], params[1], params[2]);
		}
		
		@Override
		protected void onProgressUpdate(Void... params) {

		}
		
		@Override
		protected void onPostExecute(ArrayList<Purchase> result) {
			purchaseHistoryListView = (ListView) findViewById(R.id.purchase_history_list_view);
			
			mPurchaseList = result;
			arrayAdapter = new ArrayAdapter<Purchase>(mContext, android.R.layout.simple_list_item_2, android.R.id.text1, mPurchaseList){
			    @Override
				public View getView(int position, View convertView, ViewGroup parent) {
			        View view = super.getView(position, convertView, parent);
			        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
			        text1.setText(mPurchaseList.get(position).getDate());
			        text2.setText(mPurchaseList.get(position).getBranch());
			        return view;
			      }
			    };
			purchaseHistoryListView.setAdapter(arrayAdapter);
			purchaseHistoryListView.setOnItemClickListener(mItemClickListener);
		}
		
		public ArrayList<Purchase> connectToDatabase(String user_id, String dateFrom, String dateTill) {
		    
			Connection conn = null;
			ArrayList<Purchase> resultArrayList = null;
			
			try {
		        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		        String username = "Admin";
		        String password = "BIP_project";
		        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + Utils.serverIp + ":1433/BIP_project;user=" + username + ";password=" + password);
		 
		        Log.w("Connection","open");
		        Statement statement = conn.createStatement();
		        ResultSet resultSet;
		        if (dateFrom == null) {
		        	resultSet = statement.executeQuery("SELECT purchase_id, date_time, name FROM PurchaseHistory " +
			        		"JOIN Branch on PurchaseHistory.branch_id = Branch.branch_id" +
			        		" WHERE [user_id] = " + user_id + " order by date_time desc");
		        }
		        else {
		        	Log.i("user_id", user_id);
		        	Log.i("dateFrom", dateFrom);
		        	Log.i("dateTill", dateTill);
		        	resultSet = statement.executeQuery("SELECT purchase_id, date_time, name FROM PurchaseHistory " +
			        		"JOIN Branch on PurchaseHistory.branch_id = Branch.branch_id" +
			        		" WHERE [user_id] = " + user_id + " and date_time >= '" + dateFrom + "' and date_time <= '" + dateTill + "' " +
			        				"order by date_time desc");
		        }

		        resultArrayList = new ArrayList<Purchase>();
				try {
					while (resultSet != null && resultSet.next()) {
						resultArrayList.add(new Purchase(resultSet.getString("date_time"), resultSet.getString("purchase_id"), resultSet.getString("name")));
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
