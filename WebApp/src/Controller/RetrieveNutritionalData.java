package Controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Brand;
import Model.Product;

public class RetrieveNutritionalData extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		//response.setCharacterEncoding( "UTF-8" );
		
		try {
			String barcode = request.getParameter("barcode");
			if (request.getParameter("check") == null) {
				Product product = getProductInfo(barcode);
				List<String> customCats = null;
				if (request.getParameter("userID").length() > 0) {
					customCats = Product.GetProductCustomCategory(barcode, request.getParameter("userID"));
				}
				request.setAttribute("data", product);
				request.setAttribute("customCats", customCats);
				getServletConfig().getServletContext()
						.getRequestDispatcher("/index.jsp")
						.forward(request, response);
			} else {
				response.setContentType("text/plain");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(Product.isDuplicatedId(barcode)?"1":"0");
			}
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
				request.setAttribute("data", "1");
				getServletConfig().getServletContext()
						.getRequestDispatcher("/addNewProduct.jsp")
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
