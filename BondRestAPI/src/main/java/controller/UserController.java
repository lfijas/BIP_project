package controller;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import model.MainData;
import model.EmptyObject;
import model.User;
import model.PurchaseHistory;
import model.Product;
import model.CollectionList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Link;
import org.apache.commons.codec.binary.Base64;

@RestController
public class UserController {

    @RequestMapping("/users/{userID}")
    @ResponseBody
    public HttpEntity<MainData> user(@RequestHeader("Authorization") String Authorization, @PathVariable int userID) {
    	MainData main = new MainData();
    	HttpStatus status;

    	String[] us_pass = getUsernameAndPassword(Authorization);
		String username = us_pass[0];
		String password = us_pass[1];

		int user_id = User.login(username, password);
		if(user_id != 0)
		{
			if (user_id == userID) {
				User user = User.getUserInfo(userID);
		        if (user != null) {
			        user.add(linkTo(methodOn(UserController.class).user(Authorization, userID)).withSelfRel());
			        user.add(new Link("/users/" + userID + "/password", "password"));
			        user.add(new Link("/users/" + userID + "/purchases", "purchases"));
			        
			        main.setData(user);
			        main.setSuccess(true);
			        main.setStatus(200);
			        status = HttpStatus.OK;
		        } else {
		        	main.setData(null);
			        main.setSuccess(false);
			        main.setStatus(400);
			        
			        status = HttpStatus.BAD_REQUEST;
		        }
			} else {
				System.out.println("You don't have the right to view this information!");
				main.setData(null);
		        main.setSuccess(false);
		        main.setStatus(403);
		        
		        status = HttpStatus.FORBIDDEN;
			}				
		}else{
			System.out.println("Password is not correct!");
			main.setData(null);
	        main.setSuccess(false);
	        main.setStatus(401);
	        
	        status = HttpStatus.UNAUTHORIZED;
		}
        
        return new ResponseEntity<MainData>(main, status);
    }
    
    @RequestMapping("/users/{userID}/purchases")
    @ResponseBody
    public HttpEntity<MainData> purchases(@RequestHeader("Authorization") String Authorization, @PathVariable int userID) {
    	MainData main = new MainData();
    	HttpStatus status;
    	
    	String[] us_pass = getUsernameAndPassword(Authorization);
		String username = us_pass[0];
		String password = us_pass[1];

		int user_id = User.login(username, password);
		if(user_id != 0)
		{
			if (user_id == userID) {
				EmptyObject ob = new EmptyObject();
		    	List<PurchaseHistory> purchases = PurchaseHistory.getPurchasesForUser(userID);
		        if (purchases != null) {
		        	ob.add(linkTo(methodOn(UserController.class).purchases(Authorization, userID)).withSelfRel());
		            for (PurchaseHistory purchase : purchases) {
		            	purchase.add(linkTo(methodOn(UserController.class).purchases(Authorization, userID)).slash(purchase.getPurchaseID()).withSelfRel());
		            	purchase.add(new Link("/users/" + userID + "/purchases/" + purchase.getPurchaseID() + "/products", "products"));
		            }
		            ob.add(new Link("/users/" + userID + "/purchases/date/{date}", "date"));
		            ob.embedResource("purchases", purchases);
			        
			        main.setData(ob);
			        main.setSuccess(true);
			        main.setStatus(200);
			        status = HttpStatus.OK;
		        } else {
		        	main.setData(null);
			        main.setSuccess(false);
			        main.setStatus(400);
			        
			        status = HttpStatus.BAD_REQUEST;
		        }
			} else {
				System.out.println("You don't have the right to view this information!");
				main.setData(null);
		        main.setSuccess(false);
		        main.setStatus(403);
		        
		        status = HttpStatus.FORBIDDEN;
			}				
		}else{
			System.out.println("Password is not correct!");
			main.setData(null);
	        main.setSuccess(false);
	        main.setStatus(401);
	        
	        status = HttpStatus.UNAUTHORIZED;
		}
        
        return new ResponseEntity<MainData>(main, status);
    }
    
    @RequestMapping(value="/users/{userID}/purchases", method=RequestMethod.POST)
    @ResponseBody
    public HttpEntity<MainData> createPurchase(@RequestHeader("Authorization") String Authorization, @PathVariable int userID, int branchID, String[] barcodes) {
    	MainData main = new MainData();
    	HttpStatus status;
    	
    	String[] us_pass = getUsernameAndPassword(Authorization);
		String username = us_pass[0];
		String password = us_pass[1];

		int user_id = User.login(username, password);
		if(user_id != 0)
		{
			if (PurchaseHistory.createPurchase(userID, branchID, barcodes) != 0) {
		        main.setSuccess(true);
		        main.setStatus(200);
		        status = HttpStatus.OK;
	    	} else {
		        main.setSuccess(false);
		        main.setStatus(400);
		        status = HttpStatus.BAD_REQUEST;
	    	}				
		}else{
			System.out.println("Password is not correct!");
			main.setData(null);
	        main.setSuccess(false);
	        main.setStatus(401);
	        
	        status = HttpStatus.UNAUTHORIZED;
		}
    	
    	return new ResponseEntity<MainData>(main, status);
    }
    
    @RequestMapping(value="/users", method=RequestMethod.POST)
    @ResponseBody
    public HttpEntity<MainData> createUser(User user) {
    	MainData main = new MainData();
    	HttpStatus status;
    	if (user.register() != 0) {
	        main.setSuccess(true);
	        main.setStatus(200);
	        status = HttpStatus.OK;
    	} else {
	        main.setSuccess(false);
	        main.setStatus(400);
	        status = HttpStatus.BAD_REQUEST;
    	}
    	return new ResponseEntity<MainData>(main, status);
    }
    
    @RequestMapping(value="/users/{userID}", method=RequestMethod.DELETE)
    @ResponseBody
    public HttpEntity<MainData> deleteUser(@RequestHeader("Authorization") String Authorization, @PathVariable int userID) {
    	MainData main = new MainData();
    	HttpStatus status;
    	
    	String[] us_pass = getUsernameAndPassword(Authorization);
		String username = us_pass[0];
		String password = us_pass[1];

		int user_id = User.login(username, password);
		System.out.println(user_id);
		if(user_id != 0)
		{
			if (User.deleteUser(userID) != 0) {
		        main.setSuccess(true);
		        main.setStatus(200);
		        status = HttpStatus.OK;
	    	} else {
		        main.setSuccess(false);
		        main.setStatus(400);
		        status = HttpStatus.BAD_REQUEST;
	    	}				
		}else{
			System.out.println("Password is not correct!");
			main.setData(null);
	        main.setSuccess(false);
	        main.setStatus(401);
	        
	        status = HttpStatus.UNAUTHORIZED;
		}
    	
    	return new ResponseEntity<MainData>(main, status);
    }
    
    @RequestMapping(value="/users/{userID}/password", method=RequestMethod.PUT)
    @ResponseBody
    public HttpEntity<MainData> updatePassword(@RequestHeader("Authorization") String Authorization, @PathVariable int userID, String password) {
    	MainData main = new MainData();
    	HttpStatus status;
    	
    	String[] us_pass = getUsernameAndPassword(Authorization);
		String username = us_pass[0];
		String u_password = us_pass[1];

		int user_id = User.login(username, u_password);
		if(user_id != 0)
		{
			if (user_id == userID) {
				if (User.updatePassword(userID, password) != 0) {
			        main.setSuccess(true);
			        main.setStatus(200);
			        status = HttpStatus.OK;
		    	} else {
			        main.setSuccess(false);
			        main.setStatus(400);
			        status = HttpStatus.BAD_REQUEST;
		    	}
			} else {
				System.out.println("You don't have the right to view this information!");
				main.setData(null);
		        main.setSuccess(false);
		        main.setStatus(403);
		        
		        status = HttpStatus.FORBIDDEN;
			}				
		}else{
			System.out.println("Password is not correct!");
			main.setData(null);
	        main.setSuccess(false);
	        main.setStatus(401);
	        
	        status = HttpStatus.UNAUTHORIZED;
		}
    
    	return new ResponseEntity<MainData>(main, status);
    }
    
    @RequestMapping("/users/{userID}/purchases/{purchaseID}/products")
    @ResponseBody
    public HttpEntity<CollectionList> products(@RequestHeader("Authorization") String Authorization, @PathVariable int userID, @PathVariable int purchaseID) {
    	CollectionList main = new CollectionList();
    	HttpStatus status;
    	
    	String[] us_pass = getUsernameAndPassword(Authorization);
		String username = us_pass[0];
		String password = us_pass[1];

		int user_id = User.login(username, password);
		if(user_id != 0)
		{
			if (user_id == userID) {
				List<Product> products = PurchaseHistory.getProductsForUserPurchase(userID, purchaseID);
		    	if (products != null) {
		    		main.setData(products);
			        main.setSuccess(true);
			        main.setStatus(200);
			        status = HttpStatus.OK;
		    	} else {
		    		main.setData(null);
			        main.setSuccess(false);
			        main.setStatus(400);
			        status = HttpStatus.BAD_REQUEST;
		    	}
			} else {
				System.out.println("You don't have the right to view this information!");
				main.setData(null);
		        main.setSuccess(false);
		        main.setStatus(403);
		        
		        status = HttpStatus.FORBIDDEN;
			}				
		}else{
			System.out.println("Password is not correct!");
			main.setData(null);
	        main.setSuccess(false);
	        main.setStatus(401);
	        
	        status = HttpStatus.UNAUTHORIZED;
		}
    	
    	return new ResponseEntity<CollectionList>(main, status);
    }
    
    @RequestMapping("/users/{userID}/purchases/date/{date}")
    @ResponseBody
    public HttpEntity<MainData> purchasesFilterByDate(@RequestHeader("Authorization") String Authorization, @PathVariable int userID, @PathVariable String date) {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date startDate = null;
	    try {
	        startDate = df.parse(date);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    java.sql.Date sqlDate = new java.sql.Date(startDate.getTime());
		
    	MainData main = new MainData();
    	HttpStatus status;
    	
    	String[] us_pass = getUsernameAndPassword(Authorization);
		String username = us_pass[0];
		String password = us_pass[1];

		int user_id = User.login(username, password);
		if(user_id != 0)
		{
			if (user_id == userID) {
				EmptyObject ob = new EmptyObject();
		    	List<PurchaseHistory> purchases = PurchaseHistory.getPurchasesForUserFilterByDate(userID, sqlDate);
		        if (purchases != null) {
		        	ob.add(linkTo(methodOn(UserController.class).purchasesFilterByDate(Authorization, userID, date)).withSelfRel());
		            for (PurchaseHistory purchase : purchases) {
		            	purchase.add(linkTo(methodOn(UserController.class).purchases(Authorization, userID)).slash(purchase.getPurchaseID()).withSelfRel());
		            	purchase.add(new Link("/users/" + userID + "/purchases/" + purchase.getPurchaseID() + "/products", "products"));
		            }
		            ob.embedResource("purchases", purchases);
			        
			        main.setData(ob);
			        main.setSuccess(true);
			        main.setStatus(200);
			        status = HttpStatus.OK;
		        } else {
		        	main.setData(null);
			        main.setSuccess(false);
			        main.setStatus(400);
			        
			        status = HttpStatus.BAD_REQUEST;
		        }
			} else {
				System.out.println("You don't have the right to view this information!");
				main.setData(null);
		        main.setSuccess(false);
		        main.setStatus(403);
		        
		        status = HttpStatus.FORBIDDEN;
			}				
		}else{
			System.out.println("Password is not correct!");
			main.setData(null);
	        main.setSuccess(false);
	        main.setStatus(401);
	        
	        status = HttpStatus.UNAUTHORIZED;
		}
        
        return new ResponseEntity<MainData>(main, status);
    }
    
    private String[] getUsernameAndPassword(String secret) {
		String[] result = null;
		if (secret != null && secret.toUpperCase().startsWith("BASIC ")) {
			try {
				String str = new String(Base64.decodeBase64(secret.substring(6)), "UTF-8");
				result = str.split(":");
				System.out.println(result);
			}
			catch (Exception e) { e.printStackTrace(); }
		}
		return result;
	}
    
}