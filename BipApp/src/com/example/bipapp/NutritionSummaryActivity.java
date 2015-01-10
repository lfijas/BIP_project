package com.example.bipapp;


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
import android.view.Window;
import android.widget.Toast;
import android.widget.LinearLayout;

import org.afree.data.category.DefaultCategoryDataset;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vamhan on 12/17/14 AD.
 */
public class NutritionSummaryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Intent intent = getIntent();
            String purchaseId = intent.getStringExtra(PurchaseSummaryActivity.EXTRA_MESSAGE);
            Log.i("result", purchaseId);
            new DbConnection(NutritionSummaryActivity.this).execute(purchaseId);
        }
        else {
            Toast.makeText(NutritionSummaryActivity.this, R.string.no_network, Toast.LENGTH_LONG).show();
        }
    }

    private class DbConnection extends AsyncTask<String, Void, List<Product2>> {

        private Context mContext;

        public DbConnection(Context context) {
            mContext = context;
        }

        @Override
        protected List<Product2> doInBackground(String... params) {
            return connectToDatabase(params[0]);
        }

        @Override
        protected void onProgressUpdate(Void... params) {
        }

        @Override
        protected void onPostExecute(List<Product2> products) {
            BarChartView mView = new BarChartView(mContext);

            double maxN = 0;
            double max = 0;
            double totalSize = 0;
            double calories = 0;
            double proteins = 0;
            double carbohydrates = 0;
            double sugar = 0;
            double fat = 0;
            double saturatedFat = 0;
            double cholesterol = 0;
            double fiber = 0;
            double sodium = 0;
            double vitaminA = 0;
            double vitaminC = 0;
            double calcium = 0;
            double iron = 0;
            double fruit = 0;
            double vegetable = 0;
            double meat = 0;
            double grain = 0;
            double dairy = 0;
            double confection = 0;
            double water = 0;
            for (int i = 0; i < products.size(); i++) {
                String sizeVal = "-";
                Product2 product = products.get(i);
                int amount = product.getQuantity();
                if (product.getSize() >= 0) {
                    sizeVal = (product.getSize()) + " " + product.getUnitSize();
                    double sizeGr = convertSize(product.getSize() * amount, product.getUnitSize());
                    totalSize += sizeGr;
                    calories += convertNutri(product.getSize(), product.getUnitSize(), product.getCalories()) * amount;
                    proteins += convertNutri(product.getSize(), product.getUnitSize(), product.getProteins()) * amount;
                    carbohydrates += convertNutri(product.getSize(), product.getUnitSize(), product.getCarbohydrates()) * amount;
                    sugar += convertNutri(product.getSize(), product.getUnitSize(), product.getSugar()) * amount;
                    fat += convertNutri(product.getSize(), product.getUnitSize(), product.getFat()) * amount;
                    saturatedFat += convertNutri(product.getSize(), product.getUnitSize(), product.getSaturatedFat()) * amount;
                    cholesterol += convertNutri(product.getSize(), product.getUnitSize(), product.getCholesterol()) * amount;
                    fiber += convertNutri(product.getSize(), product.getUnitSize(), product.getFiber()) * amount;
                    sodium += convertNutri(product.getSize(), product.getUnitSize(), product.getSodium()) * amount;
                    vitaminA += convertNutri(product.getSize(), product.getUnitSize(), product.getVitaminA()) * amount;
                    vitaminC += convertNutri(product.getSize(), product.getUnitSize(), product.getVitaminC()) * amount;
                    calcium += convertNutri(product.getSize(), product.getUnitSize(), product.getCalcium()) * amount;
                    iron += convertNutri(product.getSize(), product.getUnitSize(), product.getIron()) * amount;
                    switch (product.getFoodGroup().charAt(0)) {
                        case 'F':
                            fruit += sizeGr;
                            break;
                        case 'V':
                            vegetable += sizeGr;
                            break;
                        case 'M':
                            meat += sizeGr;
                            break;
                        case 'G':
                            grain += sizeGr;
                            break;
                        case 'D':
                            dairy += sizeGr;
                            break;
                        case 'C':
                            confection += sizeGr;
                            break;
                        case 'W':
                            water += sizeGr;
                            break;
                    }
                }
            }

            DefaultCategoryDataset dataSetN = new DefaultCategoryDataset();
            int countRow = 0;
            if (proteins > 0) {
                dataSetN.setValue(percentage(totalSize, proteins), "Food Group", "Proteins");
                maxN = proteins > maxN ? proteins : maxN;
                countRow++;
            }
            if (carbohydrates > 0) {
                dataSetN.setValue(percentage(totalSize, carbohydrates), "Food Group", "Carbohydrates");
                maxN = carbohydrates > maxN ? carbohydrates : maxN;
                countRow++;
            }
            if (sugar > 0) {
                dataSetN.setValue(percentage(totalSize, sugar), "Food Group", "Sugar");
                maxN = sugar > maxN ? sugar : maxN;
                countRow++;
            }
            if (fat > 0) {
                dataSetN.setValue(percentage(totalSize, fat), "Food Group", "Fat");
                maxN = fat > maxN ? fat : maxN;
                countRow++;
            }
            if (saturatedFat > 0) {
                dataSetN.setValue(percentage(totalSize, saturatedFat), "Food Group", "Saturated Fat");
                maxN = saturatedFat > maxN ? saturatedFat : maxN;
                countRow++;
            }
            if (cholesterol > 0) {
                dataSetN.setValue(percentage(totalSize, cholesterol), "Food Group", "Cholesterol");
                maxN = cholesterol > maxN ? cholesterol : maxN;
                countRow++;
            }
            if (fiber > 0) {
                dataSetN.setValue(percentage(totalSize, fiber), "Food Group", "Fiber");
                maxN = fiber > maxN ? fiber : maxN;
                countRow++;
            }
            if (sodium > 0) {
                dataSetN.setValue(percentage(totalSize, sodium), "Food Group", "Sodium");
                maxN = sodium > maxN ? sodium : maxN;
                countRow++;
            }
            if (vitaminA > 0) {
                dataSetN.setValue(percentage(totalSize, vitaminA), "Food Group", "Vitamin A");
                maxN = vitaminA > maxN ? vitaminA : maxN;
                countRow++;
            }
            if (vitaminC > 0) {
                dataSetN.setValue(percentage(totalSize, vitaminC), "Food Group", "Vitamin C");
                maxN = vitaminC > maxN ? vitaminC : maxN;
                countRow++;
            }
            if (calcium > 0) {
                dataSetN.setValue(percentage(totalSize, calcium), "Food Group", "Calcium");
                maxN = calcium > maxN ? calcium : maxN;
                countRow++;
            }
            if (iron > 0) {
                dataSetN.setValue(percentage(totalSize, iron), "Food Group", "Iron");
                maxN = iron > maxN ? iron : maxN;
                countRow++;
            }
            mView.drawChart(dataSetN, "Total Nutrition Percentage", "Nutritional Data", maxN, true);

            //generate food group bar chart
            BarChartView mView2 = new BarChartView(mContext);
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            dataSet.setValue(percentage(totalSize, fruit), "Food Group", "fruit");
            dataSet.setValue(percentage(totalSize, vegetable), "Food Group", "vegetable");
            dataSet.setValue(percentage(totalSize, meat), "Food Group", "meat");
            dataSet.setValue(percentage(totalSize, grain), "Food Group", "grains, beans and legumes");
            dataSet.setValue(percentage(totalSize, dairy), "Food Group", "dairy");
            dataSet.setValue(percentage(totalSize, confection), "Food Group", "confections");
            dataSet.setValue(percentage(totalSize, water), "Food Group", "water");
            max = Math.max(fruit, Math.max(vegetable, Math.max(meat, Math.max(grain, Math.max(dairy, Math.max(confection, water))))));
            mView2.drawChart(dataSet, "Total Food Group Percentage", "Food Groups", max, true);

            setContentView(mView);
        }

        public List<Product2> connectToDatabase(String purchaseId) {
            List<Product2> products = Product2.getProductsByPurchase(purchaseId);
            return products;
        }

        private double convertSize(double size, String unit) {
            double result = size;
            if (unit.equals("kg") || unit.equals("ltr")) {
                result *= 1000;
            } else if (unit.equals("cl")) {
                result *= 10;
            } else if (unit.equals("lbs")) {
                result *= 453.592;
            }
            return roundTo2Decimals(result);
        }

        private double convertNutri(double size, String unit, double nutri) {
            if (nutri == -1) {
                nutri = 0;
            }
            double result = size * nutri;
            if (unit.equals("kg") || unit.equals("ltr")) {
                result *= 10;
            } else if (unit.equals("cl")) {
                result *= 0.1;
            } else if (unit.equals("lbs")) {
                result *= 4.53592;
            } else {
                result *= 0.01;
            }
            return roundTo2Decimals(result);
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

    }
}
