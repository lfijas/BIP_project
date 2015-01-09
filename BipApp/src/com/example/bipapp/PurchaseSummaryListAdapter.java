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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PurchaseSummaryListAdapter extends BaseAdapter {

	private ArrayList<Product> listData;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private String mBarcode;
	private String mUserId;
	
	public PurchaseSummaryListAdapter(Context context, ArrayList<Product> listData) {
		mContext = context;
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.purchase_summary_item, null);
			holder = new ViewHolder();
			holder.text1 = (TextView) convertView.findViewById(R.id.purchase_summary_item_text1);
			holder.text2 = (TextView) convertView.findViewById(R.id.purchase_summary_item_text2);
			holder.addCategoryButton = (Button) convertView.findViewById(R.id.add_category_button);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		Log.i("getView", "position: " + position);
		holder.text1.setText(listData.get(position).getName());
		holder.text2.setText(listData.get(position).getQuantityPrice());
		holder.addCategoryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences((Activity) mContext);
				Log.i("onClick", "mPosition: " + position);
				mBarcode = listData.get(position).getBarcode();
				mUserId = Integer.toString(sp.getInt("user_id", -1));
				new DbConnection((Activity) mContext).execute(mBarcode, mUserId);
			}
		});
		
		return convertView;
	}
	
	private class DbConnection extends AsyncTask<String, Void, ArrayList<String>> {

        private Context mContext;

        public DbConnection(Context context) {
            mContext = context;
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            return connectToDatabase(params[0], params[1]);
        }

        @Override
        protected void onProgressUpdate(Void... params) {
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
        	String[] resultArray = result.toArray(new String[result.size()]);
        	CustomCategoryDialogFragment dialog = CustomCategoryDialogFragment.newInstance(resultArray, mBarcode, mUserId);
			dialog.show(((Activity) mContext).getFragmentManager(), "dialog");
        }

        public ArrayList<String> connectToDatabase(String barcode, String userId) {

            Connection conn = null;
            ArrayList<String> resultArrayList = null;

            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                String username = "Admin";
                String password = "BIP_project";
                conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + Utils.serverIp + ":1433/BIP_project;user=" + username + ";password=" + password);

                Log.w("Connection","open");
                Statement statement = conn.createStatement();
                String query = "SELECT distinct custom_category_name FROM [CustomCategory] "
                		+ "join Product_CustomCategory on Product_CustomCategory.custom_cat_id = CustomCategory.custom_cat_id "
                		+ "join Product on Product_CustomCategory.barcode = Product.barcode "
                        + "WHERE Product.barcode = " + barcode
                        + " and CustomCategory.user_id = " + userId;
                Log.i("get categories assigned to product", query);
                ResultSet resultSet = statement.executeQuery(query);


                resultArrayList = new ArrayList<String>();
                try {
                    while (resultSet != null && resultSet.next()) {
                        resultArrayList.add(resultSet.getString("custom_category_name"));
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
	
	static class ViewHolder {
		TextView text1;
		TextView text2;
		Button addCategoryButton;
	}

}
