package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;

public class PurchaseHistory extends ResourceSupport {
	private int purchaseID;
	private Date date;
	private String branch;
	private String branch_district;
	private String branch_city;
	private String branch_country;
	
	public int getPurchaseID() {
		return purchaseID;
	}




	public void setPurchaseID(int purchaseID) {
		this.purchaseID = purchaseID;
	}




	public String getBranch() {
		return branch;
	}




	public void setBranch(String branch) {
		this.branch = branch;
	}




	public String getBranch_district() {
		return branch_district;
	}




	public void setBranch_district(String branch_district) {
		this.branch_district = branch_district;
	}




	public String getBranch_city() {
		return branch_city;
	}




	public void setBranch_city(String branch_city) {
		this.branch_city = branch_city;
	}




	public String getBranch_country() {
		return branch_country;
	}




	public void setBranch_country(String branch_country) {
		this.branch_country = branch_country;
	}




	public Date getDate() {
		return date;
	}




	public void setDate(Date date) {
		this.date = date;
	}

	
	public static List<PurchaseHistory> getPurchasesForUser(int userID) {
		List<PurchaseHistory> purchases = new ArrayList<>();
		DBConnector.connect();
		
		ResultSet result = DBConnector.query("SELECT p.purchase_id, p.date_time, b.name, b.district, b.city, b.countrys from PurchaseHistory p, Branch b WHERE p.branch_id = b.branch_id and p.user_id = '" + userID + "'");
		
		try {
			while (result.next()) {
				PurchaseHistory purchase = new PurchaseHistory();
				purchase.purchaseID = result.getInt("purchase_id");
				purchase.date = result.getDate("date_time");
				purchase.branch = result.getString("name");
				purchase.branch_district = result.getString("district");
				purchase.branch_city = result.getString("city");
				purchase.branch_country = result.getString("countrys");
				purchases.add(purchase);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnector.closeConnection();
		return purchases;
	}
	
	
	public static List<Product> getProductsForUserPurchase(int purchaseID) {
		List<Product> products = new ArrayList<>();
		DBConnector.connect();
		
		ResultSet result = DBConnector.query("SELECT pro.* from Purchase_product pp, Product pro "
				+ "WHERE pp.barcode = pro.barcode and pp.purchase_id = " + purchaseID);
		
		try {
			while (result.next()) {
				Product product = new Product();
				Product.setProduct(product, result);
				products.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnector.closeConnection();
		return products;
	}
	
	
	/*public static int createPurchase(PurchaseHistory purchase) {
		DBConnector.connect();
		String query = "INSERT INTO PurchaseHistory (user_id, branch_id, date_time)"
				+ " VALUES (" + purchase.userID + "," + purchase.branchID + ",'" + purchase.date + "');";
		System.out.println(query);
		int status = DBConnector.update(query);
		ResultSet result = DBConnector.query("SELECT purchase_id from PurchaseHistory WHERE user_id = " + purchase.userID + " and date_time='" + purchase.date + "'");
		
		try {
			if (result != null && result.next()) {
				int purchaseID = result.getInt("purchase_id");
				for (String barcode : purchase.productList.keySet()) {
					query = "INSERT INTO Purchase_Product (purchase_id, barcode, quantity)"
							+ " VALUES (" + purchaseID + "," + barcode + ",'" + purchase.productList.get(barcode) + "');";
					System.out.println(query);
					status = DBConnector.update(query);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnector.closeConnection();
		return status;
	}*/
}
