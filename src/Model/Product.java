package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import Model.DBConnector;


public class Product {
	private String code;
	private String name;	
	private double size;
	private String unitSize;
	private String brand;
	private double	calories;
	private double	proteins;
	private double	carbohydrates;
	private double	sugar;
	private double	fat;
	private double	saturatedFat;
	private double	cholesterol;	
	private double	fiber;
	private double	sodium;
	private double	vitaminA;
	private double	vitaminC;
	private double	calcium;	
	private double	iron;
	
	public String getCode() {
		return code;
	}



	public String getName() {
		return name;
	}



	public double getSize() {
		return size;
	}



	public String getUnitSize() {
		return unitSize;
	}



	public String getBrand() {
		return brand;
	}



	public double getCalories() {
		return calories;
	}



	public double getProteins() {
		return proteins;
	}



	public double getCarbohydrates() {
		return carbohydrates;
	}



	public double getSugar() {
		return sugar;
	}



	public double getFat() {
		return fat;
	}



	public double getSaturatedFat() {
		return saturatedFat;
	}



	public double getCholesterol() {
		return cholesterol;
	}



	public double getFiber() {
		return fiber;
	}



	public double getSodium() {
		return sodium;
	}



	public double getVitaminA() {
		return vitaminA;
	}



	public double getVitaminC() {
		return vitaminC;
	}



	public double getCalcium() {
		return calcium;
	}



	public double getIron() {
		return iron;
	}																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		

	
	
	public static Product GetProductByBarcode(String barcode)
	{
		Product product = null;
		
		try {
			
			DBConnector.connect();
			
			ResultSet result = DBConnector.query("SELECT * from Product p, Brand b WHERE p.brand_id = b.id and barcode = '" + barcode + "'");
			
			if (result != null && result.next())
			{
				product = new Product();
				product.code = result.getString("barcode");
				product.name = (String) isNull(result, "product_name", "string");
				product.brand = (String) isNull(result, "brand_name", "string");
				//product.size = result.getDouble("quantity");
				product.unitSize = (String) isNull(result, "quantity", "string");
				product.calories = (double) isNull(result, "calories", "double");
				product.proteins = (double) isNull(result, "proteins_100", "double");
				product.carbohydrates = (double) isNull(result, "carbohydrates_100", "double");
				product.sugar = (double) isNull(result, "sugar_100", "double");
				product.fat = (double) isNull(result, "fat_100", "double");
				product.saturatedFat = (double) isNull(result, "saturated_fat_100", "double");
				product.cholesterol = (double) isNull(result, "cholesterol_100", "double");
				product.fiber = (double) isNull(result, "fiber_100", "double");
				product.sodium = (double) isNull(result, "sodium_100", "double");
				product.vitaminA = (double) isNull(result, "vitamin_a", "double");
				product.vitaminC = (double) isNull(result, "vitamin_c", "double");
				product.calcium = (double) isNull(result, "calcium_100", "double");
				product.iron = (double) isNull(result, "iron_100", "double");
				
			}
			
			DBConnector.closeConnection();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}
	
	private static Object isNull(ResultSet result, String field, String type) {
		try {
			if (result.getObject(field) != null) {
				if (type == "double") {
					return roundTo2Decimals(result.getDouble(field));
				} else {
					return result.getString(field);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (type == "double") {
			return -1.0;
		} else {
			return "-";
		}
	}
	
	private static double roundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
	}

}
