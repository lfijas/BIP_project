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
import android.content.Intent;
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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class FilterSummaryActivity extends Activity {

	private RelativeLayout mRelativeLayout;
	private String mYear;
	private String mMeasure;
	private JSONArray mResult;
	private Context mContext;
	private String mDateFrom;
	private String mDateTill;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        //mDateFrom = intent.getExtras().getString("date_from");
        //mDateTill = intent.getExtras().getString("date_till");
        mDateFrom = "10/10/2014";
        mDateTill = "21/11/2014";
        Log.i("dateFrom", mDateFrom);
        Log.i("dateTill", mDateTill);
        
        setContentView(R.layout.activity_filter_summary);
        
        mContext = this;
        
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_filter_summary_layout);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user_id = Integer.toString(settings.getInt("user_id", -1));
        if (networkInfo != null && networkInfo.isConnected()) {
            if (!user_id.equals("-1")) {
                new DbConnection(FilterSummaryActivity.this).execute(user_id, mDateFrom, mDateTill);
            }
            else {
                Toast.makeText(FilterSummaryActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(FilterSummaryActivity.this, R.string.no_network, Toast.LENGTH_LONG).show();
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
		
		DefaultCategoryDataset dataSetN = new DefaultCategoryDataset();
        int countRow = 0;
        try {
            JSONObject job = mResult.getJSONObject(0);
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
        p.addRule(RelativeLayout.BELOW, R.id.filter_summary_text);
        mView.setLayoutParams(p);
        
        mRelativeLayout.addView(mView);
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
            return sumNutritionGroupByMonth(params[0], params[1], params[2]);
        }

        @Override
        protected void onProgressUpdate(Void... params) {

        }

        @Override
        protected void onPostExecute(JSONArray result) {
        	mResult = result;
            drawChart();
        }

        public JSONArray sumNutritionGroupByMonth(String userid, String startDate, String endDate) {
            JSONArray jarray = null;
            try {
                HttpClient client = new DefaultHttpClient();
                String getURL;
                if (startDate.equals("") || endDate.equals("")) {
                	getURL = "http://54.149.71.241/Nutrition/sumNutritionGroupByAllPurchases?userID=" + userid;
                }
                else {
                	getURL = "http://54.149.71.241/Nutrition/sumNutritionGroupByAllPurchases?userID=" + userid + 
                			"&startDate=" + startDate + "&endDate=" + endDate;
                
                }
                Log.i("getURL", getURL);
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
