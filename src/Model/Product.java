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
		
		ResultSet result = DBConnector.query("SELECT * from Product p, Brand b WHERE p.brand_id = b.id and barcode = '" + barcode + "'");
		
		try {
			if (result != null && result.next()) {
				product = new Product();
				product.code = result.getString("barcode");
				product.name = (String) isNull(result, "product_name", "string");
				product.brand = (String) isNull(result, "brand_name", "string");
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnector.closeConnection();
		return product;
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
		String foodGroup = product.foodGroup;
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
