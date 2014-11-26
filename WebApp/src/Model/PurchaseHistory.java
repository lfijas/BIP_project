package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class PurchaseHistory {
	private int purchaseID;
	private int userID;
	private int branchID;
	private Date datetime;
	private Map<String, Integer> productList;
	
	public int getPurchaseID() {
		return purchaseID;
	}


	public void setPurchaseID(int purchaseID) {
		this.purchaseID = purchaseID;
	}


	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}


	public int getBranchID() {
		return branchID;
	}


	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}


	public Date getDatetime() {
		return datetime;
	}


	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}


	public Map<String, Integer> getProductList() {
		return productList;
	}


	public void setProductList(Map<String, Integer> productList) {
		this.productList = productList;
	}
	
	
	public static int createPurchase(PurchaseHistory purchase) {
		DBConnector.connect();
		String query = "INSERT INTO PurchaseHistory (user_id, branch_id, date_time)"
				+ " VALUES (" + purchase.userID + "," + purchase.branchID + ",'" + purchase.datetime + "');";
		System.out.println(query);
		int status = DBConnector.update(query);
		ResultSet result = DBConnector.query("SELECT purchase_id from PurchaseHistory WHERE user_id = " + purchase.userID + " and date_time='" + purchase.datetime + "'");
		
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
	}
}
