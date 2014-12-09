package Model;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class OracleConnector {

	static String host = "192.168.56.12";
	static String port = "1521";
	static String username = "WCDataWarehouse";
	static String password = "WCDataWarehouse";
	static String sid = "orcl";
	
	static Connection conn;
	
	public static void main(String[] args) {
		connect();
	}
	
	public static Connection connect(){
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		 
		try {
 
			Class.forName("oracle.jdbc.driver.OracleDriver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return null;
 
		}
 
		System.out.println("Oracle JDBC Driver Registered!");
 
		//Connection connection = null;
 
		try {
 
	        String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
	        String username = "WCDataWarehouse";
	        String password = "WCDataWarehouse";
	        conn = DriverManager.getConnection(url, username, password);
			
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@okypurwanti:1521:orcl" + ";user=" + username + ";password=" + password + ";database=" + database);
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
 
		}
 
		if (conn != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return conn;
	}
	
	public static void closeConn() throws SQLException{
		conn.close();
	}
}


