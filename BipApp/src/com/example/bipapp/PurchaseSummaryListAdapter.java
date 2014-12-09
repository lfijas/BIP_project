package com.example.bipapp;

import java.util.ArrayList;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseSummaryListAdapter extends BaseAdapter {

	private ArrayList<Product> listData;
	private LayoutInflater layoutInflater;
	
	public PurchaseSummaryListAdapter(Context context, ArrayList<Product> listData) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
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
		holder.text1.setText(listData.get(position).getName());
		holder.text2.setText(listData.get(position).getQuantityPrice());
		holder.addCategoryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("lukasz", "lukasz");
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView text1;
		TextView text2;
		Button addCategoryButton;
	}

}
