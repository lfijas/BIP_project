package controller;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import model.EmptyObject;
import model.User;
import model.PurchaseHistory;
import model.Product;
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
    public HttpEntity<User> user(@PathVariable int userID) {
        User user = User.getUserInfo(userID);
        user.add(linkTo(methodOn(UserController.class).user(userID)).withSelfRel());
        //Method method = UserController.class.getMethod("purchases", Long.class);
        user.add(new Link("http://localhost:8080/users/" + userID + "/password", "purchases"));
        user.add(new Link("http://localhost:8080/users/" + userID + "/purchases", "purchases"));
        
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    @RequestMapping("/users/{userID}/purchases")
    @ResponseBody
    public HttpEntity<EmptyObject> purchases(@PathVariable int userID) {
    	EmptyObject ob = new EmptyObject();
    	ob.add(linkTo(methodOn(UserController.class).purchases(userID)).withSelfRel());
        List<PurchaseHistory> purchases = PurchaseHistory.getPurchasesForUser(userID);
        for (PurchaseHistory purchase : purchases) {
        	ob.add(new Link("http://localhost:8080/users/" + userID + "/purchases/" + purchase.getPurchaseID() + "/products", "products"));
        	ob.add(new Link("http://localhost:8080/users/" + userID + "/purchases/date/" + purchase.getDate(), "date"));
        	purchase.add(linkTo(methodOn(UserController.class).purchases(userID)).slash(purchase.getPurchaseID()).withSelfRel());
        }
        ob.embedResource("purchases", purchases);
        
        
        return new ResponseEntity<EmptyObject>(ob, HttpStatus.OK);
    }
    
    @RequestMapping(value="/users", method=RequestMethod.POST)
    @ResponseBody
    public HttpEntity<User> createUser(User user) {
    	int status = user.register();
    	if (status != 0) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
    	}
    }
    
    @RequestMapping(value="/users/{userID}", method=RequestMethod.DELETE)
    @ResponseBody
    public HttpEntity<EmptyObject> deleteUser(@PathVariable int userID) {
    	int status = User.deleteUser(userID);
    	EmptyObject ob = new EmptyObject();
    	if (status != 0) {
            return new ResponseEntity<EmptyObject>(ob, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<EmptyObject>(ob, HttpStatus.BAD_REQUEST);
    	}
    }
    
    @RequestMapping(value="/users/{userID}/password", method=RequestMethod.PUT)
    @ResponseBody
    public HttpEntity<EmptyObject> updatePassword(@PathVariable int userID, String password) {
    	int status = User.updatePassword(userID, password);
    	EmptyObject ob = new EmptyObject();
    	if (status != 0) {
            return new ResponseEntity<EmptyObject>(ob, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<EmptyObject>(ob, HttpStatus.BAD_REQUEST);
    	}
    }
    
    @RequestMapping("/users/{userID}/purchases/{purchaseID}/products")
    @ResponseBody
    public HttpEntity<List<Product>> products(@PathVariable int userID, @PathVariable int purchaseID) {
    	//EmptyObject ob = new EmptyObject();
    	//ob.add(linkTo(methodOn(UserController.class).products(userID)).withSelfRel());
        List<Product> products = PurchaseHistory.getProductsForUserPurchase(purchaseID);
        /*for (PurchaseHistory purchase : purchases) {
        	ob.add(new Link("http://localhost:8080/users/" + userID + "/purchases/" + purchase.getPurchaseID() + "/products", "products"));
        	ob.add(new Link("http://localhost:8080/users/" + userID + "/purchases/date/" + purchase.getDate(), "date"));
        	purchase.add(linkTo(methodOn(UserController.class).purchases(userID)).slash(purchase.getPurchaseID()).withSelfRel());
        }
        ob.embedResource("purchases", purchases);*/
        
        
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
    
}