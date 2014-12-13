package model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import model.DBConnector;


public class User extends ResourceSupport{
	private String username;
	private String password;
	private Sex sex;
	private int age;
	private String email;
	private String address;
	private String city;
	private String country;
	private Date birthdate;
	private String phone;
	
	/*public static void main(String[] args) {
		getUserInfo(1);
	} */
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public enum Sex {
        M,
        F;
    }
	
	public User() {
		
	}
	
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
	
	public static User getUserInfo(int userID) {
		User user = new User();
		DBConnector.connect();
		
		ResultSet result = DBConnector.query("SELECT * from [BIP_project].[dbo].[User] WHERE user_id = '" + userID + "'");
		
		try {
			if (result != null && result.next()) {
				user.username = result.getString("username");
				user.sex = result.getString("gender") == "F" ? Sex.F : Sex.M ;
				user.email = result.getString("email");
				user.address = result.getString("address");
				user.city = result.getString("city");
				user.country = result.getString("country");
				user.birthdate = result.getDate("birthdate");
				user.phone = result.getString("phone");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnector.closeConnection();
		return user;
	}

}
