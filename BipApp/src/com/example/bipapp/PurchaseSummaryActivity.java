package com.example.bipapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import org.afree.data.category.DefaultCategoryDataset;

public class PurchaseSummaryActivity extends Activity {

	private ListView purchaseSummaryListView;
	private PurchaseSummaryListAdapter purchaseSummaryListAdapter;
	//private ArrayAdapter<Product> arrayAdapter;
	private ArrayList<Product> mProductList;
	private Button mViewSummaryButton;

	private OnItemClickListener mItemClickListener = null;
	
	
	public final static String EXTRA_MESSAGE = "com.example.bipapp.message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase_summary);
		
		purchaseSummaryListView = (ListView) findViewById(R.id.purchase_summary_list_view);
		mViewSummaryButton = (Button) findViewById(R.id.view_purchase_summary_button);
		
		if (mItemClickListener == null) {
			mItemClickListener = new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					Intent intent = new Intent(PurchaseSummaryActivity.this, NutritionInfoActivity.class);
					String productBarcode = ((Product) purchaseSummaryListAdapter.getItem(arg2)).getBarcode();
					intent.putExtra(EXTRA_MESSAGE, productBarcode);
					startActivity(intent);
				}
			};
		}
		
		mViewSummaryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                Intent oldIntent = getIntent();
                String purchaseId = oldIntent.getStringExtra(BrowsePurchaseHistoryActivity.EXTRA_MESSAGE);
				Intent intent = new Intent(PurchaseSummaryActivity.this, NutritionSummaryActivity.class);
                intent.putExtra(EXTRA_MESSAGE, purchaseId);
				startActivity(intent);
			}
		});
		
		if (savedInstanceState != null && savedInstanceState.containsKey("purchase_products")) {
			mProductList = savedInstanceState.getParcelableArrayList("purchase_products");
			purchaseSummaryListAdapter = new PurchaseSummaryListAdapter(this, mProductList);
			purchaseSummaryListView = (ListView) findViewById(R.id.purchase_summary_list_view);
			purchaseSummaryListView.setAdapter(purchaseSummaryListAdapter);
			purchaseSummaryListView.setOnItemClickListener(mItemClickListener);
		}
		else {
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				Intent intent = getIntent();
				String purchaseId = intent.getStringExtra(BrowsePurchaseHistoryActivity.EXTRA_MESSAGE);
				new DbConnection(PurchaseSummaryActivity.this).execute(purchaseId);
			}
			else {
				Toast.makeText(PurchaseSummaryActivity.this, R.string.no_network, Toast.LENGTH_LONG).show();
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.purchase_summary, menu);
		return true;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("purchase_products", mProductList);
		super.onSaveInstanceState(outState);
	}
	
	private class DbConnection extends AsyncTask<String, Void, ArrayList<Product>> {

        private Context mContext;

        public DbConnection(Context context) {
            mContext = context;
        }

        @Override
        protected ArrayList<Product> doInBackground(String... params) {
            return connectToDatabase(params[0]);
        }

        @Override
        protected void onProgressUpdate(Void... params) {
        }

        @Override
        protected void onPostExecute(ArrayList<Product> result) {
            mProductList = result;
            purchaseSummaryListAdapter = new PurchaseSummaryListAdapter(mContext, mProductList);
            purchaseSummaryListView.setAdapter(purchaseSummaryListAdapter);
            purchaseSummaryListView.setOnItemClickListener(mItemClickListener);
        }

        public ArrayList<Product> connectToDatabase(String purchaseId) {

            Connection conn = null;
            ArrayList<Product> resultArrayList = null;

            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                String username = "Admin";
                String password = "BIP_project";
                conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + Utils.serverIp + ":1433/BIP_project;user=" + username + ";password=" + password);

                Log.w("Connection","open");
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT product_name, Product.barcode AS barcode, price, quantity FROM Purchase_Product " +
                        "JOIN Product ON Purchase_Product.barcode = Product.barcode " +
                        "WHERE purchase_id = " + purchaseId);


                resultArrayList = new ArrayList<Product>();
                try {
                    while (resultSet != null && resultSet.next()) {
                        resultArrayList.add(new Product(resultSet.getString("product_name").
                                replaceAll("&quot;", "\""), resultSet.getString("barcode"),
                                resultSet.getString("quantity"), resultSet.getString("price")));
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
