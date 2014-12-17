package model;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.DBConnector;

import org.springframework.hateoas.ResourceSupport;


public class Product extends ResourceSupport{
	private String barcode;
	private String product_name;
	private int food_group_id;
	private double price;
	private double quantity_number;
	private String unit;
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
	
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getFood_group_id() {
		return food_group_id;
	}

	public void setFood_group_id(int food_group_id) {
		this.food_group_id = food_group_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getQuantity_number() {
		return quantity_number;
	}

	public void setQuantity_number(double quantity_number) {
		this.quantity_number = quantity_number;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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
	
			
	public static List<Product> getAllProducts()
	{	
		List<Product> products = new ArrayList<Product>();
		DBConnector.connect();
		
		ResultSet result = DBConnector.query("SELECT * from Product p LEFT JOIN Brand b on p.brand_id = b.id LEFT JOIN Food_groups g on p.group_id = g.id");
		
		try {
			while (result.next()) {
				Product product = new Product();
				setProduct(product, result);
				products.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnector.closeConnection();
		return products;
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
		List<String> customCats = new ArrayList<String>();
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
	
	public static int addProduct(Product product) {
		DBConnector.connect();
		String query = "INSERT INTO Product (barcode, product_name, group_id, price"
				+ ") VALUES (" + product.barcode + ",'" + product.product_name + "'," + product.food_group_id + "," + product.price + ");";
		System.out.println(query);
		int status = DBConnector.update(query);
		DBConnector.closeConnection();
		return status;
	}
	
	public static int updateProduct(Product product) {
		DBConnector.connect();
		String query = "UPDATE Product set product_name = '" + product.product_name + "', group_id = " + product.food_group_id + ", price = " + product.price;
		System.out.println(query);
		int status = DBConnector.update(query);
		DBConnector.closeConnection();
		return status;
	}
	
	public static void setProduct(Product product, ResultSet result) throws SQLException {
		product.barcode = result.getString("barcode");
		product.product_name = result.getString("product_name");
		product.food_group_id = result.getInt("group_id");
		product.quantity_number = (double) isNull(result, "quantity_number", "double");
		product.unit = result.getString("unit");
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
	
	private static double isNull(ResultSet result, String field, String type) {
		try {
			if (result.getObject(field) != null) {
				return roundTo2Decimals(result.getDouble(field));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0.0;
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
