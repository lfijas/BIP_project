package Model;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
	
	static final String host = "54.149.71.241";
	static final String port = "1433";
	static final String username = "Admin";
	static final String password = "BIP_project";
	static final String database = "BIP_project";
	
	static Connection conn;
	
	public static void main(String[] args) {
		connect("Bond_DW");
	}
	
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
	
	public static Connection connectWithCatelog(String database, String catelog) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:mondrian:Jdbc=jdbc:sqlserver://" + host + ":" + port + ";jdbc.databaseName=" + database + ";Catalog=" + catelog + ";JdbcDrivers=com.microsoft.sqlserver.jdbc.SQLServerDriver;JdbcUser=" + username + ";JdbcPassword=" + password);
			System.out.println("DB connect success");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
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
