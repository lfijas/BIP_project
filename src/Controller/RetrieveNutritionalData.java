package Controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Product;

public class RetrieveNutritionalData extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding( "UTF-8" );
		
		String barcode = request.getParameter("barcode");
		Product product = Product.GetProductByBarcode(barcode);
		
		
		try {
			request.setAttribute("data", product);
			getServletConfig().getServletContext()
					.getRequestDispatcher("/index.jsp")
					.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
