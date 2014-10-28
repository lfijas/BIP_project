package Model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

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
			
			ResultSet result = DBConnector.query("SELECT * from Products WHERE barcode = '" + barcode + "'");
			
			if (result != null && result.next())
			{
				product = new Product();
				product.code = result.getString("barcode");
				product.name = result.getString("name");
				//product.size = result.getDouble("size");
				//product.unitSize = result.getString("unit_size");
				product.calories = result.getDouble("calories");
				product.proteins = result.getDouble("proteins");
				product.carbohydrates = result.getDouble("carbohydrates");
				product.sugar = result.getDouble("sugar");
				product.fat = result.getDouble("fat");
				product.saturatedFat = result.getDouble("saturated_fat");
				product.cholesterol = result.getDouble("cholesterol");
				product.fiber = result.getDouble("fiber");
				product.sodium = result.getDouble("sodium");
				product.vitaminA = result.getDouble("vitamin_a");
				product.vitaminC = result.getDouble("vitamin_c");
				product.calcium = result.getDouble("calcium");
				product.iron = result.getDouble("iron");
				
			}
			
			DBConnector.closeConnection();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

}
