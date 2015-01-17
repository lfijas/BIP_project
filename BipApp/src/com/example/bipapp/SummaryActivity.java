package com.example.bipapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
		
		for (int i = 0; i < customCategoryListSize; i++) {
			String cat = sp.getString("customCat_" + i, "");
			categories.add(cat);
		}
		categories.add("Fruit");
		categories.add("Vegetable");
		ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, categories);
		categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCategorySpinner.setAdapter(categoriesAdapter);
	}

    private class DbConnection extends AsyncTask<String, Void, JSONArray> {

        private Context mContext;
        private String year;
        private String measure;

        public DbConnection(Context context) {
            mContext = context;
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            year = "2014";
            //measure = "calories";
            measure = params[2];
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

        public JSONArray sumNutritionGroupByMonth(String userid, String year) {
            JSONArray jarray = null;
            try {
                HttpClient client = new DefaultHttpClient();

                String getURL = "http://54.149.71.241/Nutrition/sumNutritionGroupByMonth?userID=" + userid + "&year=" + year;
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
