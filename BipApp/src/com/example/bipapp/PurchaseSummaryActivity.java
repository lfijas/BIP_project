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
				Intent intent = new Intent(PurchaseSummaryActivity.this, NutritionSummaryActivity.class);
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
            List<Product2> products = Product2.getProductsByPurchase(purchaseId);


            resultArrayList = new ArrayList<Product>();
            for (Product2 product : products) {
                resultArrayList.add(new Product(product.getName().
                            replaceAll("&quot;", "\""), product.getCode(),
                            product.getQuantity(), product.getPrice()));
            }

            //setPurchasesSummary(products);

			return resultArrayList;
		}

        public void setPurchasesSummary(List<Product2> products) {

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
                        case 'F':  fruit += sizeGr;
                            break;
                        case 'V':  vegetable += sizeGr;
                            break;
                        case 'M':  meat += sizeGr;
                            break;
                        case 'G':  grain += sizeGr;
                            break;
                        case 'D':  dairy += sizeGr;
                            break;
                        case 'C':  confection += sizeGr;
                            break;
                        case 'W':  water += sizeGr;
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

        }

	}

    private double convertSize(double size, String unit){
        double result = size;
        if (unit.equals("kg") || unit.equals("ltr")) {
            result *=  1000;
        } else if (unit.equals("cl")) {
            result *=  10;
        } else if (unit.equals("lbs")) {
            result *=  453.592;
        }
        return roundTo2Decimals(result);
    }
    private double convertNutri(double size, String unit, double nutri){
        if (nutri == -1) {
            nutri = 0;
        }
        double result = size * nutri;
        if (unit.equals("kg") || unit.equals("ltr")) {
            result *=  10;
        } else if (unit.equals("cl")) {
            result *=  0.1;
        } else if (unit.equals("lbs")) {
            result *=  4.53592;
        } else {
            result *= 0.01;
        }
        return roundTo2Decimals(result);
    }

    private double percentage(double size, double nutri){
        if (size != 0) {
            return roundTo2Decimals((nutri * 100.0) / size);
        } else {
            return 0;
        }
    }

    private double roundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }

}
