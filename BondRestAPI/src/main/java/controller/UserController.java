package controller;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Link;

@Controller
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/users/{userID}")
    @ResponseBody
    public HttpEntity<MainData> user(@PathVariable int userID) {
    	MainData main = new MainData();
    	HttpStatus status;
        
    	User user = User.getUserInfo(userID);
        if (user != null) {
	        user.add(linkTo(methodOn(UserController.class).user(userID)).withSelfRel());
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
        
        return new ResponseEntity<MainData>(main, status);
    }
    
    @RequestMapping("/users/{userID}/purchases")
    @ResponseBody
    public HttpEntity<MainData> purchases(@PathVariable int userID) {
    	MainData main = new MainData();
    	HttpStatus status;
        
    	EmptyObject ob = new EmptyObject();
    	List<PurchaseHistory> purchases = PurchaseHistory.getPurchasesForUser(userID);
        if (purchases != null) {
        	ob.add(linkTo(methodOn(UserController.class).purchases(userID)).withSelfRel());
            for (PurchaseHistory purchase : purchases) {
            	purchase.add(linkTo(methodOn(UserController.class).purchases(userID)).slash(purchase.getPurchaseID()).withSelfRel());
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
        
        return new ResponseEntity<MainData>(main, status);
    }
    
    @RequestMapping(value="/users/{userID}/purchases", method=RequestMethod.POST)
    @ResponseBody
    public HttpEntity<MainData> createPurchase(@PathVariable int userID, int branchID, String[] barcodes) {
    	MainData main = new MainData();
    	HttpStatus status;
    	if (PurchaseHistory.createPurchase(userID, branchID, barcodes) != 0) {
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
    public HttpEntity<MainData> deleteUser(@PathVariable int userID) {
    	MainData main = new MainData();
    	HttpStatus status;
    	if (User.deleteUser(userID) != 0) {
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
    
    @RequestMapping(value="/users/{userID}/password", method=RequestMethod.PUT)
    @ResponseBody
    public HttpEntity<MainData> updatePassword(@PathVariable int userID, String password) {
    	MainData main = new MainData();
    	HttpStatus status;
    	System.out.println(password);
    	if (User.updatePassword(userID, password) != 0) {
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
    
    @RequestMapping("/users/{userID}/purchases/{purchaseID}/products")
    @ResponseBody
    public HttpEntity<CollectionList> products(@PathVariable int userID, @PathVariable int purchaseID) {
    	CollectionList main = new CollectionList();
    	HttpStatus status;
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
    	return new ResponseEntity<CollectionList>(main, status);
    }
    
    @RequestMapping("/users/{userID}/purchases/date/{date}")
    @ResponseBody
    public HttpEntity<MainData> purchasesFilterByDate(@PathVariable int userID, @PathVariable String date) {
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
        
    	EmptyObject ob = new EmptyObject();
    	List<PurchaseHistory> purchases = PurchaseHistory.getPurchasesForUserFilterByDate(userID, sqlDate);
    	System.out.println(sqlDate);
    	System.out.println(purchases);
        if (purchases != null) {
        	ob.add(linkTo(methodOn(UserController.class).purchasesFilterByDate(userID, date)).withSelfRel());
            for (PurchaseHistory purchase : purchases) {
            	purchase.add(linkTo(methodOn(UserController.class).purchases(userID)).slash(purchase.getPurchaseID()).withSelfRel());
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
        
        return new ResponseEntity<MainData>(main, status);
    }
    
}