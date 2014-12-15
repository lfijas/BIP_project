package main.java.controller;

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
import model.FoodGroup;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Link;
import org.apache.commons.codec.binary.Base64;

public class FoodGroupController {
	
	
    @RequestMapping("/foodgroup/{name}/products")
    @ResponseBody
    public HttpEntity<CollectionList> products(@PathVariable String name) {
    	CollectionList main = new CollectionList();
    	HttpStatus status;
    	List<Product> products = FoodGroup.getProductsFromFoodGroup(name);
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
    
    @RequestMapping(value="/foodgroups", method=RequestMethod.POST)
    @ResponseBody
    public HttpEntity<MainData> addFoodGroup(String name) {
    	MainData main = new MainData();
    	HttpStatus status;
    	if (name.length() != 0 && FoodGroup.addFoodGroup(name) != 0) {
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
}
