package com.example.bipapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.afree.data.category.DefaultCategoryDataset;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;


public class SummaryActivity extends Activity {

	private RelativeLayout mRelativeLayout;
	private Spinner mYearSpinner;
	private Spinner mMonthSpinner;
	private Spinner mMeasureSpinner;
	private Spinner mCategorySpinner;
	private String mYear;
	private String mMeasure;
	private JSONArray mResult;
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_activity);
        
        mContext = this;
        
        mYear = "2014";
        mMeasure = "calories";
        
        mRelativeLayout = (RelativeLayout) findViewById(R.id.graph_purchase_summary_layout);
        mYearSpinner = (Spinner) findViewById(R.id.years_spinner);
        mYearSpinner.setSelection(1);
        mMonthSpinner = (Spinner) findViewById(R.id.months_spinner);
        mMeasureSpinner = (Spinner) findViewById(R.id.measure_spinner);
        mCategorySpinner = (Spinner) findViewById(R.id.category_spinner);
        /*mMonthSummaryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("year", String.valueOf(mYearSpinner.getSelectedItem()));
				Log.i("month", String.valueOf(mMonthSpinner.getSelectedItem()));
				
			}
		});*/
        
        loadCategoriesToSpinner();
        
        mCategorySpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        		mMeasureSpinner.setVisibility(View.VISIBLE);
        		mMonthSpinner.setSelection(0);
        		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String user_id = Integer.toString(settings.getInt("user_id", -1));
                String cat = parent.getItemAtPosition(pos).toString();
                if (cat.equals("All categories")) {
                	new DbConnection(SummaryActivity.this).execute(user_id, mYear, mMeasure);
                }
                else {
	                String isCustomCat = "true";
	                if (cat.equals("Fruit") || cat.equals("Vegetable") || cat.equals("Meat") || cat.equals("Grains, beans, and legumes")
	                		|| cat.equals("Dairy") || cat.equals("Confections") || cat.equals("Water") || cat.equals("Miscellaneous")) {
	                	isCustomCat = "false";
	                }
	                Log.i("year", mYear);
	        		new DbConnection(SummaryActivity.this).execute(user_id, mYear, mMeasure, cat, isCustomCat);
                }
        	}
        });
        
        mYearSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(){
        	
        	@Override
        	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String user_id = Integer.toString(settings.getInt("user_id", -1));
                mYear = parent.getItemAtPosition(pos).toString();
                Log.i("year", mYear);
        		new DbConnection(SummaryActivity.this).execute(user_id, mYear, mMeasure);
        	  }
        });
        
        mMeasureSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        		mMeasure = parent.getItemAtPosition(pos).toString().toLowerCase();
                Log.i("measure", mMeasure);
        		drawChart();
        	  }
        });
        
        mMonthSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        		if (pos != 0) {
        			mMeasureSpinner.setVisibility(View.INVISIBLE);
	        		DefaultCategoryDataset dataSetN = new DefaultCategoryDataset();
	                int countRow = 0;
	                try {
		                JSONObject job = mResult.getJSONObject(pos - 1);
		                double totalSize = job.getDouble("size");
		                if (job.getDouble("proteins") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("proteins")), "Food Group", "Proteins");
		                    countRow++;
		                }
		                if (job.getDouble("carbohydrates") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("carbohydrates")), "Food Group", "Carbohydrates");
		                    countRow++;
		                }
		                if (job.getDouble("sugar") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("sugar")), "Food Group", "Sugar");
		                    countRow++;
		                }
		                if (job.getDouble("fat") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("fat")), "Food Group", "Fat");
		                    countRow++;
		                }
		                if (job.getDouble("saturatedFat") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("saturatedFat")), "Food Group", "Saturated Fat");
		                    countRow++;
		                }
		                if (job.getDouble("cholesterol") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("cholesterol")), "Food Group", "Cholesterol");
		                    countRow++;
		                }
		                if (job.getDouble("fiber") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("fiber")), "Food Group", "Fiber");
		                    countRow++;
		                }
		                if (job.getDouble("sodium") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("sodium")), "Food Group", "Sodium");
		                    countRow++;
		                }
		                if (job.getDouble("vitaminA") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("vitaminA")), "Food Group", "Vitamin A");
		                    countRow++;
		                }
		                if (job.getDouble("vitaminC") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("vitaminC")), "Food Group", "Vitamin C");
		                    countRow++;
		                }
		                if (job.getDouble("calcium") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("calcium")), "Food Group", "Calcium");
		                    countRow++;
		                }
		                if (job.getDouble("iron") > 0) {
		                    dataSetN.setValue(percentage(totalSize, job.getDouble("iron")), "Food Group", "Iron");
		                    countRow++;
		                }
	                } catch (JSONException e) {
	                	e.printStackTrace();
	                }
	                
	                BarChartView mView = new BarChartView(mContext);
	                mView.drawChart(dataSetN, "Total Nutrition Percentage", "Nutritional Data", 0, true);
	                
	                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
	                        ViewGroup.LayoutParams.WRAP_CONTENT);
	                p.addRule(RelativeLayout.BELOW, R.id.spinner_layout2);
	                mView.setLayoutParams(p);
	                
	                mRelativeLayout.addView(mView);
        		}
        		else {
        			mMeasureSpinner.setVisibility(View.VISIBLE);
        			drawChart();
        		}
        	 }
        });
        

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user_id = Integer.toString(settings.getInt("user_id", -1));
        if (networkInfo != null && networkInfo.isConnected()) {
            if (!user_id.equals("-1")) {
                new DbConnection(SummaryActivity.this).execute(user_id, mYear, mMeasure);
            }
            else {
                Toast.makeText(SummaryActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(SummaryActivity.this, R.string.no_network, Toast.LENGTH_LONG).show();
        }
	}
	
	private double percentage(double size, double nutri) {
        if (size != 0) {
            Log.i("result", roundTo2Decimals((nutri * 100.0) / size) + "");
            return roundTo2Decimals((nutri * 100.0) / size);
        } else {
            Log.i("result", "0");
            return 0;
        }
    }

    private double roundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }
	
	public void drawChart() {

        LineChartView mView = new LineChartView(mContext);
        mView.drawChart(mResult, mMeasure, mYear);
        
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.BELOW, R.id.spinner_layout2);
        mView.setLayoutParams(p);
        
        mRelativeLayout.addView(mView);
	}
	
	private void loadCategoriesToSpinner() {
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
		int customCategoryListSize = sp.getInt("numberOfCustomCat", 0);
		
		List<String> categories = new ArrayList<String>();
		categories.add("All categories");
		
		for (int i = 0; i < customCategoryListSize; i++) {
			String cat = sp.getString("customCat_" + i, "");
			categories.add(cat);
		}
		categories.add("Fruit");
		categories.add("Vegetable");
		categories.add("Meat");
		categories.add("Grains, beans, and legumes");
		categories.add("Dairy");
		categories.add("Confections");
		categories.add("Water");
		categories.add("Miscellaneous");
		ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, categories);
		categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCategorySpinner.setAdapter(categoriesAdapter);
	}

    private class DbConnection extends AsyncTask<String, Void, JSONArray> {

        private Context mContext;
        private String year;
        private String measure;
        private String category;
        private String isCustomCategory;

        public DbConnection(Context context) {
            mContext = context;
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            year = "2014";
            //measure = "calories";
            measure = params[2];
            if (params.length == 5) {
            	category = params[3];
            	isCustomCategory = params[4];
            }
            return sumNutritionGroupByMonth(params[0], params[1]);
        }

        @Override
        protected void onProgressUpdate(Void... params) {

        }

        @Override
        protected void onPostExecute(JSONArray result) {
        	//mResult = new JSONArray();
        	mResult = result;
            drawChart();
        }
        
        private String getCategoryIdByName(String name, String isCustom) {
        	
        	Connection conn = null;
            ArrayList<Product> resultArrayList = null;
            String categoryId = null;

            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                String username = "Admin";
                String password = "BIP_project";
                conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + Utils.serverIp + ":1433/BIP_project;user=" + username + ";password=" + password);

                Log.w("Connection","open");
                Statement statement = conn.createStatement();
                ResultSet resultSet;
                if(isCustom.equals("true")) {
                	resultSet = statement.executeQuery("SELECT TOP 1 [custom_cat_id] as [id]" +
                			  " FROM [CustomCategory]" +
                					  " where custom_category_name = '" + name + "'");
                }
                else {
                	resultSet = statement.executeQuery("SELECT TOP 1 [id] FROM [Food_groups]" +
                					"where [group_name] = '" + name + "'");
                }

                try {
                    if (resultSet != null && resultSet.next()) {
                        categoryId = resultSet.getString("id");
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
            return categoryId;
        }
    

        public JSONArray sumNutritionGroupByMonth(String userid, String year) {
            JSONArray jarray = null;
            try {
                HttpClient client = new DefaultHttpClient();
                String getURL;
                if (category != null && isCustomCategory != null) {
                	getURL = "http://54.149.71.241/Nutrition/sumNutritionGroupByMonth?userID=" + userid + "&year=" + year 
                			+ "&categoryID=" + getCategoryIdByName(category, isCustomCategory) + "&isCustom=" + isCustomCategory;
                }
                else {
                	getURL = "http://54.149.71.241/Nutrition/sumNutritionGroupByMonth?userID=" + userid + "&year=" + year;
                }
                HttpGet get = new HttpGet(getURL);
                get.setHeader("Accept", "application/json");
                HttpResponse responseGet = client.execute(get);
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        responseGet.getEntity().getContent(), "utf-8"));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                jarray = new JSONArray(result.toString());

                rd.close();
                get.abort();
            } catch (Exception e) {
                Log.w("Error connection","" + e.getMessage());
                e.printStackTrace();
            }
            return jarray;
        }
    }

}
