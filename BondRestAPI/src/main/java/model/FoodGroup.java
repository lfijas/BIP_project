package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DBConnector;

public class FoodGroup {

	private String name;
	
	public static void main(String[] args) {
		
		List<Product> products = getProductsFromFoodGroup("Fruit");
	
		for(int i = 0; i < products.size(); i++) {
	            System.out.println(products);
	        }

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static int getFoodGroupId(String name) {
			
		DBConnector.connect();
		ResultSet result = DBConnector.query("SELECT id from Food_groups WHERE group_name like '%" + name + "%'");
		try {
			if (result != null && result.next()) {
				return result.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnector.closeConnection();
		return 0;
	}
	
	public static int addFoodGroup(String name) {
		int id = 0;
		DBConnector.connect();
		DBConnector.update("INSERT INTO Food_groups (name) VALUES ('" + name + "');");
		ResultSet result = DBConnector.query("SELECT id from Food_groups WHERE name = '" + name + "'");
		
		try {
			if (result != null && result.next()) {
				return result.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnector.closeConnection();
		return id;
	}
	
	public static List<Product> getProductsFromFoodGroup(String name){
		List<Product> products = new ArrayList<>();
		DBConnector.connect();
		
		ResultSet result = DBConnector.query("SELECT p.barcode, p.group_id, p.product_name, p.price, p.quantity_number, p.unit, p.serving_size, p.calories, p.proteins_100, p.carbohydrates_100, p.sugar_100, p.fat_100, p.saturated_fat_100, p.fiber_100, p.cholesterol_100, p.sodium_100, p.calcium_100, p.iron_100, p.vitamin_a, p.vitamin_c FROM Product p LEFT JOIN Food_groups f on p.group_id = f.id WHERE f.group_name = '"+name+"'");
		
		try {
			while(result.next()) {
				Product product = new Product();
				Product.setProduct(product, result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnector.closeConnection();
		return products;
	}
	
	
}
