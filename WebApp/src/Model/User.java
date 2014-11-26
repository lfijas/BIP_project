package Model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class User {
	private String username;
	private String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public void register() {
		try {
			byte[] bytesOfMessage = password.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < thedigest.length; i++) {
	          sb.append(Integer.toString((thedigest[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        password = sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		DBConnector.connect();
		DBConnector.update("INSERT INTO [userProfile].[dbo].[User] (username, password, name) VALUES ('" + username + "','" + password + "', '');");
		DBConnector.closeConnection();
	}
	
	public static boolean isUserIDExist (String id) {
		DBConnector.connect();
		
		ResultSet result = DBConnector.query("SELECT user_id from [BIP_project].[dbo].[User] WHERE user_id = '" + id + "'");
		
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

}
