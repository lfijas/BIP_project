package Model;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
	private String foodGroup;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getUnitSize() {
		return unitSize;
	}

	public void setUnitSize(String unitSize) {
		this.unitSize = unitSize;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getProteins() {
		return proteins;
	}

	public void setProteins(double proteins) {
		this.proteins = proteins;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public double getSugar() {
		return sugar;
	}

	public void setSugar(double sugar) {
		this.sugar = sugar;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public double getSaturatedFat() {
		return saturatedFat;
	}

	public void setSaturatedFat(double saturatedFat) {
		this.saturatedFat = saturatedFat;
	}

	public double getCholesterol() {
		return cholesterol;
	}

	public void setCholesterol(double cholesterol) {
		this.cholesterol = cholesterol;
	}

	public double getFiber() {
		return fiber;
	}

	public void setFiber(double fiber) {
		this.fiber = fiber;
	}

	public double getSodium() {
		return sodium;
	}

	public void setSodium(double sodium) {
		this.sodium = sodium;
	}

	public double getVitaminA() {
		return vitaminA;
	}

	public void setVitaminA(double vitaminA) {
		this.vitaminA = vitaminA;
	}

	public double getVitaminC() {
		return vitaminC;
	}

	public void setVitaminC(double vitaminC) {
		this.vitaminC = vitaminC;
	}

	public double getCalcium() {
		return calcium;
	}

	public void setCalcium(double calcium) {
		this.calcium = calcium;
	}

	public double getIron() {
		return iron;
	}

	public void setIron(double iron) {
		this.iron = iron;
	}
	
	public String getFoodGroup() {
		return foodGroup;
	}

	public void setFoodGroup(String foodGroup) {
		this.foodGroup = foodGroup;
	}
	
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																
	
	public static Product GetProductByBarcode(String barcode)
	{	
		Product product = null;
		DBConnector.connect();
		
		ResultSet result = DBConnector.query("SELECT * from Product p LEFT JOIN Brand b on p.brand_id = b.id LEFT JOIN Food_groups g on p.group_id = g.id WHERE barcode = '" + barcode + "'");
		
		try {
			if (result != null && result.next()) {
				product = new Product();
				setProduct(product, result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnector.closeConnection();
		return product;
	}
	
	public static List<String> GetProductCustomCategory(String barcode, String userid)
	{	
		List<String> customCats = new ArrayList<>();
		DBConnector.connect();
		
		ResultSet result = DBConnector.query("SELECT custom_category_name from CustomCategory c, Product_CustomCategory pc WHERE barcode = " + barcode + " and user_id = " + userid + " and c.custom_cat_id = pc.custom_cat_id");
		
		try {
			while (result.next()) {
				customCats.add(result.getString("custom_category_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnector.closeConnection();
		return customCats;
	}
	
	public static List<Product> getProductsByMultipleBarcodes(String[] barcodes)
	{	
		ArrayList<Product> productList = new ArrayList<Product>();
		DBConnector.connect();
		
		String query = "SELECT * from Product p LEFT JOIN Brand b on p.brand_id = b.id LEFT JOIN Food_groups g on p.group_id = g.id WHERE (barcode = " + barcodes[0];
		for (int i = 1; i < barcodes.length; i++) {
			query += " or barcode = " + barcodes[i];
		}
		query += ")";
		ResultSet result = DBConnector.query(query);
		
		try {
			while (result.next()) {
				Product product = new Product();
				setProduct(product, result);
				productList.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnector.closeConnection();
		return productList;
	}
	
	public static Product getNutritionalSummary(String[] barcodes)
	{	
		Product product = null;
		DBConnector.connect();
		
		String query = "SELECT SUM(calories) as calories,SUM(proteins_100) as proteins_100,SUM(carbohydrates_100) as carbohydrates_100,SUM(sugar_100) as sugar_100,SUM(fat_100) as fat_100,SUM(saturated_fat_100) as saturated_fat_100"
				+ ",SUM(cholesterol_100) as cholesterol_100,SUM(fiber_100) as fiber_100,SUM(sodium_100) as sodium_100,SUM(vitamin_a) as vitamin_a,SUM(vitamin_c) as vitamin_c,SUM(calcium_100) as calcium_100,SUM(iron_100) as iron_100 from Product WHERE barcode = " + barcodes[0];
		for (int i = 1; i < barcodes.length; i++) {
			query += " or barcode = " + barcodes[i];
		}
		ResultSet result = DBConnector.query(query);
		
		try {
			if (result != null && result.next()) {
				product = new Product();
				product.calories = roundTo2Decimals(result.getDouble("calories"));
				product.proteins = roundTo2Decimals(result.getDouble("proteins_100"));			
				product.carbohydrates = roundTo2Decimals(result.getDouble("carbohydrates_100"));
				product.sugar = roundTo2Decimals(result.getDouble("sugar_100"));				
				product.fat = roundTo2Decimals(result.getDouble("fat_100"));		
				product.saturatedFat = roundTo2Decimals(result.getDouble("saturated_fat_100"));	
				product.cholesterol = roundTo2Decimals(result.getDouble("cholesterol_100"));		
				product.fiber = roundTo2Decimals(result.getDouble("fiber_100"));				
				product.sodium = roundTo2Decimals(result.getDouble("sodium_100"));		
				product.vitaminA = roundTo2Decimals(result.getDouble("vitamin_a"));			
				product.vitaminC = roundTo2Decimals(result.getDouble("vitamin_c"));
				product.calcium = roundTo2Decimals(result.getDouble("calcium_100"));				
				product.iron = roundTo2Decimals(result.getDouble("iron_100"));				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnector.closeConnection();
		return product;
	}
	
	private static void setProduct(Product product, ResultSet result) throws SQLException {
		product.code = result.getString("barcode");
		product.name = (String)isNull(result, "product_name", "string");
		product.brand = (String) isNull(result, "brand_name", "string");
		product.foodGroup = (String) isNull(result, "group_name", "string");
		product.size = (double) isNull(result, "quantity_number", "double");
		product.unitSize = (String) isNull(result, "unit", "string");
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
	
	public static boolean isDuplicatedId(String barcode) {
		DBConnector.connect();
		
		ResultSet result = DBConnector.query("SELECT barcode from Product WHERE barcode = '" + barcode + "'");
		
		try {
			if (result != null && result.next()) {
				return true;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBConnector.closeConnection();
		return false;
	}
	
	public static int addProduct(Product product) {
		String brandName = product.brand;
		String brandId = null;
		if (!brandName.isEmpty()) {
			brandId = Brand.getBrandId(brandName) + "";
			if (brandId.equals("0")) {
				brandId = Brand.addBrand(brandName) + "";
			}
		}
		int foodGroup = FoodGroup.getFoodGroupId(product.foodGroup);
		DBConnector.connect();
		String query = "INSERT INTO Product (barcode, product_name, brand_id, group_id, quantity_number, unit, calories, proteins_100, carbohydrates_100, "
				+ "sugar_100, fat_100, saturated_fat_100, cholesterol_100, fiber_100, sodium_100, vitamin_a, vitamin_c, calcium_100, iron_100"
				+ ") VALUES (" + product.code + ",'" + product.name + "'," + brandId + "," + foodGroup + "," + checkIfNumberIsNull(product.size) + "," + product.unitSize + "," + checkIfNumberIsNull(product.calories)
				+ "," + checkIfNumberIsNull(product.proteins) + "," + checkIfNumberIsNull(product.carbohydrates) + "," + checkIfNumberIsNull(product.sugar) + "," + checkIfNumberIsNull(product.fat) + "," + checkIfNumberIsNull(product.saturatedFat)
				+ "," + checkIfNumberIsNull(product.cholesterol) + "," + checkIfNumberIsNull(product.fiber) + "," + checkIfNumberIsNull(product.sodium) + "," + checkIfNumberIsNull(product.vitaminA) + "," + checkIfNumberIsNull(product.vitaminC)
				+ "," + checkIfNumberIsNull(product.calcium) + "," + checkIfNumberIsNull(product.iron) + ");";
		System.out.println(query);
		//DBConnector.update("INSERT INTO Product (barcode, product_name, brand_id, quantity_number) VALUES ('" + product.code + "','" + product.name + "','" + brandId + "'," + checkIfNumberIsNull(product.size) + ");");
		int status = DBConnector.update(query);
		DBConnector.closeConnection();
		return status;
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
	
	private static String checkIfNumberIsNull(double value) {
		if (value < 0) {
			return null;
		} else {
			return value + "";
		}
	}

}
