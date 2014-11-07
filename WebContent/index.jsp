<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="Model.Product" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="jquery/jquery.validate.js"></script>
<title>Insert title here</title>
</head>
<body>
	<form id="getNutrition" action="/Nutrition/RetrieveNutritionalData" method="get">
		product barcode: <input type="digits" name="barcode" required />
		<input type="submit" value="Submit">
		<input type="button" value="Add product" onclick="window.location.replace('addNewProduct.jsp');">
	</form>
	
	<div><% if (request.getParameter("barcode") != null) {
				if (request.getAttribute("data") != null) {
					Product product = (Product)request.getAttribute("data");
					out.print("<table><tr><td>Product Name:</td><td>" + product.getName() + "</td></tr>");
					out.print("<tr><td>Brand Name:</td><td>" + product.getBrand() + "</td></tr>");
					if (product.getSize() >= 0) {
						out.print("<tr><td>Quantity:</td><td>" + product.getSize() + " " + product.getUnitSize() + "</td></tr>");
					}
					out.print("<tr><td>Nutritional data per 100g</td><td></td></tr>");
					if (product.getCalories() >= 0) {
						out.print("<tr><td>Calories:</td><td>" + product.getCalories() + " kj</td></tr>");
					}
					if (product.getProteins() >= 0) {
						out.print("<tr><td>Proteins:</td><td>" + product.getProteins() + " g</td></tr>");
					}
					if (product.getCarbohydrates() >= 0) {
						out.print("<tr><td>Carbohydrates:</td><td>" + product.getCarbohydrates() + " g</td></tr>");
					}
					if (product.getSugar() >= 0) {
						out.print("<tr><td>Sugar:</td><td>" + product.getSugar() + " g</td></tr>");
					}
					if (product.getFat() >= 0) {
						out.print("<tr><td>Fat:</td><td>" + product.getFat() + " g</td></tr>");
					}
					if (product.getSaturatedFat() >= 0) {
						out.print("<tr><td>Saturated Fat:</td><td>" + product.getSaturatedFat() + " g</td></tr>");
					}
					if (product.getCholesterol() >= 0) {
						out.print("<tr><td>Cholesterol:</td><td>" + product.getCholesterol() + " g</td></tr>");
					}
					if (product.getFiber() >= 0) {
						out.print("<tr><td>Fiber:</td><td>" + product.getFiber() + " g</td></tr>");
					}
					if (product.getSodium() >= 0) {
						out.print("<tr><td>Sodium:</td><td>" + product.getSodium() + " g</td></tr>");
					}
					if (product.getVitaminA() >= 0) {
						out.print("<tr><td>Vitamin A:</td><td>" + product.getVitaminA() + " g</td></tr>");
					}
					if (product.getVitaminC() >= 0) {
						out.print("<tr><td>Vitamin C:</td><td>" + product.getVitaminC() + " g</td></tr>");
					}
					if (product.getCalcium() >= 0) {
						out.print("<tr><td>Calcium:</td><td>" + product.getCalcium() + " g</td></tr>");
					}
					if (product.getIron() >= 0) {
						out.print("<tr><td>Iron:</td><td>" + product.getIron() + " g</td></tr>");
					}
					out.print("</table>");
				} else {
					out.print("product is not found or the barcode is incorrect");
				}
			} %></div>
			
			
	<script type="text/javascript">
		$("#getNutrition").validate();
	</script>
</body>
</html>