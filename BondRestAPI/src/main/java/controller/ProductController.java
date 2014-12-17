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
public class ProductController {

    @RequestMapping("/products")
    @ResponseBody
    public HttpEntity<MainData> products() {
    	MainData main = new MainData();
    	HttpStatus status;

    	List<Product> products = Product.getAllProducts();
    	main.add(linkTo(methodOn(ProductController.class).products()).withSelfRel());
        for (Product product : products) {
        	main.add(new Link("products/" + product.getBarcode(), "product"));
        }
        main.setSuccess(true);
        main.setStatus(200);
        status = HttpStatus.OK;

        
        return new ResponseEntity<MainData>(main, status);
    }
    
    @RequestMapping("/products/{barcode}")
    @ResponseBody
    public HttpEntity<MainData> product(@PathVariable String barcode) {
    	MainData main = new MainData();
    	HttpStatus status;

    	Product product = Product.GetProductByBarcode(barcode);
    	if (product != null) {
    		main.setData(product);
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
    
    @RequestMapping(value="/products", method=RequestMethod.POST)
    @ResponseBody
    public HttpEntity<MainData> addProduct(Product product) {
    	MainData main = new MainData();
    	HttpStatus status;
    	if (product.getBarcode() != null && product.getBarcode().length() != 0 && product.getProduct_name() != null && product.getProduct_name().length() != 0 && Product.addProduct(product) != 0) {
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
    
    @RequestMapping(value="/products/{barcode}", method=RequestMethod.PUT)
    @ResponseBody
    public HttpEntity<MainData> updateProduct(@PathVariable String barcode, Product product) {
    	MainData main = new MainData();
    	HttpStatus status;
    	if (barcode != null && barcode.length() != 0 && product.getProduct_name() != null && product.getProduct_name().length() != 0 && Product.updateProduct(product, barcode) != 0) {
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