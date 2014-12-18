package com.example.bipapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.layout.RectangularCellSetFormatter;
import org.olap4j.metadata.Member;


public class SummaryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


    private class DbConnection extends AsyncTask<String, Void, List<Summary>> {

        private Context mContext;

        public DbConnection(Context context) {
            mContext = context;
        }

        @Override
        protected List<Summary> doInBackground(String... params) {
            return sumNutritionGroupByMonth(params[0], 2014);
        }

        @Override
        protected void onProgressUpdate(Void... params) {

        }

        @Override
        protected void onPostExecute(List<Summary> result) {
            List<Double> measures = new ArrayList<Double>();
            for (Summary summary : result) {
                measures.add(summary.getCalories());
            }
            Log.i("result", measures.toString());

            LineChartView mView = new LineChartView(mContext);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(mView);
        }

        public List<Summary> sumNutritionGroupByMonth(String userid, int year) {

            String query = "select {[Measures].[SUMsize], [Measures].[SUMcalories], [Measures].[SUMproteins], [Measures].[SUMcarbohydrates], [Measures].[SUMsugar], [Measures].[SUMfat],"
                    + "[Measures].[SUMsaturatedfat], [Measures].[SUMfiber], [Measures].[SUMchoresterol], [Measures].[SUMsodium], [Measures].[SUMcalcium]"
                    + ", [Measures].[SUMiron], [Measures].[SUMvitaminA], [Measures].[SUMvitaminC], [Measures].[SUMprice]} ON COLUMNS,"
                    + "[Date.Date Hierarchy].[YEAR].[" + year + "].children ON ROWS "
                    + "from [Nutrition_Fact] "
                    + "where([Customer.Customer Hierarchy].[ID].[" + userid + "])";

            return query(query, 1);
        }

        private List<Summary> query(String query, int titleIndex) {

            CellSet result = OLAPQuery(query);

            List<Summary> sumList = new ArrayList<Summary>();

            try {

                for (int i = 0; i < result.getAxes().get(1).getPositionCount(); i++) {

                    Summary summary = new Summary();

                    Member member = result.getAxes().get(1).getPositions().get(i).getMembers().get(titleIndex);
                    String title = "";
                    if (member != null) {
                        title = member.getName();
                    }
                    summary.setTitle(title);

                    for (int j = 0; j < result.getAxes().get(0).getPositionCount(); j++) {
                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(j);
                        list.add(i);
                        double measure = result.getCell(list).isNull() ? 0 : result.getCell(list).getDoubleValue();
                        switch (j) {
                            case 0:
                                summary.setSize(measure);
                                break;
                            case 1:
                                summary.setCalories(measure);
                                break;
                            case 2:
                                summary.setProteins(measure);
                                break;
                            case 3:
                                summary.setCarbohydrates(measure);
                                break;
                            case 4:
                                summary.setSugar(measure);
                                break;
                            case 5:
                                summary.setFat(measure);
                                break;
                            case 6:
                                summary.setSaturatedFat(measure);
                                break;
                            case 7:
                                summary.setFiber(measure);
                                break;
                            case 8:
                                summary.setCholesterol(measure);
                                break;
                            case 9:
                                summary.setSodium(measure);
                                break;
                            case 10:
                                summary.setCalcium(measure);
                                break;
                            case 11:
                                summary.setIron(measure);
                                break;
                            case 12:
                                summary.setVitaminA(measure);
                                break;
                            case 13:
                                summary.setVitaminC(measure);
                                break;
                        }

                    }
                    sumList.add(summary);
                }
            } catch (OlapException e) {
                e.printStackTrace();
            }

            return sumList;

        }

        public CellSet OLAPQuery(String query) {

            CellSet result = null;
            try {
                // Register driver.
                Class.forName("mondrian.olap4j.MondrianOlap4jDriver");
                Connection connection = DBConnector.connectWithCatelog("Bond_dw", "/Users/vamhan/Desktop/NutritionSchema2.xml");

                OlapConnection olapConnection;
                olapConnection = connection.unwrap(OlapConnection.class);


                // Prepare a statement.
                result = olapConnection.createStatement().executeOlapQuery(query);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }

}
