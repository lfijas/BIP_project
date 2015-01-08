package com.example.bipapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;


public class SummaryActivity extends Activity {

	private RelativeLayout mRelativeLayout;
	private Button mMonthSummaryButton;
	private Spinner mYearSpinner;
	private Spinner mMonthSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_activity);
        
        mRelativeLayout = (RelativeLayout) findViewById(R.id.graph_purchase_summary_layout);
        mMonthSummaryButton = (Button) findViewById(R.id.summary_by_month_button);
        mYearSpinner = (Spinner) findViewById(R.id.years_spinner);
        mMonthSpinner = (Spinner) findViewById(R.id.months_spinner);
        mMonthSummaryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("year", String.valueOf(mYearSpinner.getSelectedItem()));
				Log.i("month", String.valueOf(mMonthSpinner.getSelectedItem()));
				
			}
		});
        

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user_id = Integer.toString(settings.getInt("user_id", -1));
        if (networkInfo != null && networkInfo.isConnected()) {
            if (!user_id.equals("-1")) {
                new DbConnection(SummaryActivity.this).execute(user_id);
            }
            else {
                Toast.makeText(SummaryActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(SummaryActivity.this, R.string.no_network, Toast.LENGTH_LONG).show();
        }
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
            measure = "calories";
            return sumNutritionGroupByMonth(params[0], year);
        }

        @Override
        protected void onProgressUpdate(Void... params) {

        }

        @Override
        protected void onPostExecute(JSONArray result) {

            List<Double> measures = new ArrayList<Double>();
            try {
                for (int i = 0; i < result.length(); i++) {
                    measures.add(((JSONObject)result.get(i)).getDouble(measure));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("result", measures.toString());

            LineChartView mView = new LineChartView(mContext);
            mView.drawChart(result, measure, year);
            
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            p.addRule(RelativeLayout.BELOW, R.id.spinner_layout);
            p.addRule(RelativeLayout.ABOVE, R.id.summary_by_month_button);
            mView.setLayoutParams(p);
            
            mRelativeLayout.addView(mView);
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
