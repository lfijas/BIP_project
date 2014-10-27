package Model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

import Model.DBConnector;


public class Product {
	private String code;
	private String name;	
	private int size;
	private String unitSize;
	private String brand;
	private int	calories;
	private int	proteins;
	private int	carbohydrates;
	private int	sugar;
	private int	fat;
	private int	saturatedFat;
	private int	cholesterol;	
	private int	fiber;
	private int	sodium;
	private int	vitaminA;
	private int	vitaminC;
	private int	calcium;	
	private int	iron;
	
	public String getCode() {
		return code;
	}



	public String getName() {
		return name;
	}



	public int getSize() {
		return size;
	}



	public String getUnitSize() {
		return unitSize;
	}



	public String getBrand() {
		return brand;
	}



	public int getCalories() {
		return calories;
	}



	public int getProteins() {
		return proteins;
	}



	public int getCarbohydrates() {
		return carbohydrates;
	}



	public int getSugar() {
		return sugar;
	}



	public int getFat() {
		return fat;
	}



	public int getSaturatedFat() {
		return saturatedFat;
	}



	public int getCholesterol() {
		return cholesterol;
	}



	public int getFiber() {
		return fiber;
	}



	public int getSodium() {
		return sodium;
	}



	public int getVitaminA() {
		return vitaminA;
	}



	public int getVitaminC() {
		return vitaminC;
	}



	public int getCalcium() {
		return calcium;
	}



	public int getIron() {
		return iron;
	}																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		

	
	
	public static Product GetProductByBarcode(String barcode)
	{
		Product product = new Product();
		
		try {
			
			DBConnector.connect();
			
			ResultSet result = DBConnector.query("SELECT * from Products WHERE barcode = '" + barcode + "'");
			
			if (result != null)
			{
				result.next();
				product.code = result.getString("barcode");
				product.name = result.getString("name");
				product.size = result.getInt("size");
				product.unitSize = result.getString("unit_size");
				product.calories = result.getInt("calories");
				product.proteins = result.getInt("proteins");
				product.carbohydrates = result.getInt("carbohydrates");
				product.sugar = result.getInt("sugar");
				product.fat = result.getInt("fat");
				product.saturatedFat = result.getInt("saturated_fat");
				product.cholesterol = result.getInt("cholesterol");
				product.fiber = result.getInt("fiber");
				product.sodium = result.getInt("sodium");
				product.vitaminA = result.getInt("vitamin_a");
				product.vitaminC = result.getInt("vitamin_c");
				product.calcium = result.getInt("calcium");
				product.iron = result.getInt("iron");
				
			}
			
			DBConnector.closeConnection();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

}
