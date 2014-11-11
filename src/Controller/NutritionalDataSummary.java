package Controller;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Brand;
import Model.Product;

public class NutritionalDataSummary extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		//response.setCharacterEncoding( "UTF-8" );
		
		try {
			String[] barcodes=request.getParameterValues("barcode");
			ArrayList<Product> products = Product.getProductsByMultipleBarcodes(barcodes);
			Product summary = Product.getNutritionalSummary(barcodes);
			
			request.setAttribute("data", products);
			request.setAttribute("summary", summary);
			getServletConfig().getServletContext()
					.getRequestDispatcher("/summary.jsp")
					.forward(request, response);

		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		Product product = new Product();
		product.setCode(request.getParameter("barcode"));
		product.setName(request.getParameter("name"));
		product.setBrand(request.getParameter("brand"));
		product.setFoodGroup(request.getParameter("group"));
		product.setSize(checkNumberIsEmpty(request, "size"));
		if (checkNumberIsEmpty(request, "size") < 0) {
			product.setUnitSize(null);
		} else {
			product.setUnitSize("'" + request.getParameter("unit") + "'");
		}
		product.setCalories(checkNumberIsEmpty(request, "calory"));
		product.setProteins(checkNumberIsEmpty(request, "protein"));
		product.setCarbohydrates(checkNumberIsEmpty(request, "carb"));
		product.setSugar(checkNumberIsEmpty(request, "sugar"));
		product.setFat(checkNumberIsEmpty(request, "fat"));
		product.setSaturatedFat(checkNumberIsEmpty(request, "sat_fat"));
		product.setCholesterol(checkNumberIsEmpty(request, "choles"));
		product.setFiber(checkNumberIsEmpty(request, "fiber"));
		product.setSodium(checkNumberIsEmpty(request, "sodium"));
		product.setVitaminA(checkNumberIsEmpty(request, "vita"));
		product.setVitaminC(checkNumberIsEmpty(request, "vitc"));
		product.setCalcium(checkNumberIsEmpty(request, "calcium"));
		product.setIron(checkNumberIsEmpty(request, "iron"));
		
		int status = Product.addProduct(product);
		
		try {
				request.setAttribute("data", product);
				getServletConfig().getServletContext()
						.getRequestDispatcher("/index.jsp")
						.forward(request, response);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}

	}
	
	protected Product getProductInfo(String barcode) {
		return Product.GetProductByBarcode(barcode);
	}
	
	private double checkNumberIsEmpty(HttpServletRequest request, String param) {
		if (!request.getParameter(param).isEmpty()) {
			return Double.parseDouble(request.getParameter(param));
		} else {
			return -1;
		}
	}
}
