package Model;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
	
	static String host = "54.69.68.63";
	static String port = "1433";
	static String username = "Admin";
	static String password = "BIP_project";
	static String database = "BIP_project";
	
	static Connection conn;
	
	public static void connect() {
		connect(database);
	}

	public static void connect(String database) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://" + host + ":" + port + ";user=" + username + ";password=" + password + ";database=" + database);
			System.out.println("DB connect success");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet query(String sql) {
		ResultSet rs;
		try {
			Statement sta = conn.createStatement();
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			rs = null;
			e.printStackTrace();
		}
		return rs;
	}
	
	public static int update(String sql) {
		int rs;
		try {
			Statement sta = conn.createStatement();
			rs = sta.executeUpdate(sql);
			System.out.print(sql);
		} catch (SQLException e) {
			rs = 0;
			e.printStackTrace();
		}
		return rs;
	}
	
	public static PreparedStatement prepareStatement(String query) {
		try {
			return conn.prepareStatement(query);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public static CallableStatement prepareCall(String call) {
		try {
			return conn.prepareCall(call);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
