package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Brand {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static int getBrandId(String name) {
			
		DBConnector.connect();
		ResultSet result = DBConnector.query("SELECT id from Brand WHERE brand_name = '" + name + "'");
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
	
	public static int addBrand(String name) {
		int id = 0;
		DBConnector.connect();
		DBConnector.update("INSERT INTO Brand (brand_name) VALUES ('" + name + "');");
		ResultSet result = DBConnector.query("SELECT id from Brand WHERE brand_name = '" + name + "'");
		
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
