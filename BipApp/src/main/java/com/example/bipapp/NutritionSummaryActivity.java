package com.example.bipapp;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by vamhan on 12/17/14 AD.
 */
public class NutritionSummaryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LineChartView mView = new LineChartView(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(mView);
    }

}
