package model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import model.DBConnector;
import model.HALResource;


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
	
	public void setPassword(String password) {
		this.password = password;
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

	public void setBirthdate(String birthdate) {
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date startDate = null;
	    try {
	        startDate = df.parse(birthdate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
		this.birthdate = new java.sql.Date(startDate.getTime());
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
	
	public int register() {
		DBConnector.connect();
		int status = DBConnector.update("INSERT INTO [BIP_project].[dbo].[User] (username, password, gender, email, address, city, country, birthdate, phone) "
				+ "VALUES ('" + username + "','" + password  + "','" +  sex + "','" +  email + "','" +  address + "','" +  city + "','" +  country + "','" +  birthdate + "','" +  phone + "');");
		DBConnector.closeConnection();
		return status;
	}
	
	public static int deleteUser(int userID) {
		DBConnector.connect();
		int status = DBConnector.update("DELETE from [BIP_project].[dbo].[User] where user_id=" + userID);
		DBConnector.closeConnection();
		return status;
	}
	
	public static User getUserInfo(int userID) {
		User user = null;
		DBConnector.connect();
		
		ResultSet result = DBConnector.query("SELECT * from [BIP_project].[dbo].[User] WHERE user_id = '" + userID + "'");
		
		try {
			if (result != null && result.next()) {
				user = new User();
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
	
	public static int updatePassword(int userID, String password) {
		DBConnector.connect();
		int status = DBConnector.update("UPDATE [BIP_project].[dbo].[User] SET password='" + password + "' where user_id=" + userID);
		DBConnector.closeConnection();
		return status;
	}
	
	public static boolean isUserIDExist (int id) {
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
