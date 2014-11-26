package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodGroup {

	private String name;

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
	
}
