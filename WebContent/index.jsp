<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="Model.Product" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
	<form action="/Nutrition/RetrieveNutritionalData" method="get">
		product barcode: <input type="text" name="barcode">
		<input type="submit" value="Submit">
	</form>
	
	<div><% if (request.getAttribute("data") != null) {
			Product product = (Product)request.getAttribute("data");
			out.print(product.getName());
		} %></div>
</body>
</html>