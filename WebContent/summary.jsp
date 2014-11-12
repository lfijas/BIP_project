<%@page import="sun.text.normalizer.CharTrie.FriendAgent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="US-ASCII"%>
<%@ page import="Model.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.nio.charset.Charset" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="jquery/jquery.validate.js"></script>
<script type="text/javascript" src="jquery/jquery-ui-1.8.20.custom.min.js"></script>
<link rel="stylesheet" type="text/css" href="jquery/jquery-ui-1.8.23.custom.css" />
<title>Insert title here</title>
</head>
<body>
	<form id="getNutrition" action="/Nutrition/NutritionalDataSummary" method="get">
		<div name="barcode_div">product barcode: <input type="digits" name="barcode" required /></div>
		<input type="submit" value="Submit">
		<input type="button" value="Add product" onclick="addProduct();">
	</form>
	
	<div><% if (request.getParameter("barcode") != null) {
				if (request.getAttribute("data") != null) {
					Product summary = (Product)request.getAttribute("summary");
					out.print("<br/><table>");
					out.print("<tr><td><b>Product Name</b></td><td><b>Quantity</b></td></tr>");
					ArrayList<Product> products = (ArrayList<Product>)request.getAttribute("data");
					double totalSize = 0;
					double calories = 0;
					double proteins = 0;
					double carbohydrates = 0;
					double sugar = 0;
					double fat = 0;
					double saturatedFat = 0;
					double cholesterol = 0;	
					double fiber = 0;
					double sodium = 0;
					double vitaminA = 0;
					double vitaminC = 0;
					double calcium = 0;	
					double iron = 0;
					double fruit = 0;
					double vegetable = 0;
					double meat = 0;
					double grain = 0;
					double dairy = 0;
					double confection = 0;
					double water = 0;
					for (Product product : products) {
						String sizeVal = "-";
						if (product.getSize() >= 0) {
							sizeVal = product.getSize() + " " + product.getUnitSize();
							double sizeGr = convertSize(product.getSize(), product.getUnitSize());
							totalSize += sizeGr;
							calories += convertNutri(product.getSize(), product.getUnitSize(), product.getCalories());
							proteins += convertNutri(product.getSize(), product.getUnitSize(), product.getProteins());
							carbohydrates += convertNutri(product.getSize(), product.getUnitSize(), product.getCarbohydrates());
							sugar += convertNutri(product.getSize(), product.getUnitSize(), product.getSugar());
							fat += convertNutri(product.getSize(), product.getUnitSize(), product.getFat());
							saturatedFat += convertNutri(product.getSize(), product.getUnitSize(), product.getSaturatedFat());
							cholesterol += convertNutri(product.getSize(), product.getUnitSize(), product.getCholesterol());
							fiber += convertNutri(product.getSize(), product.getUnitSize(), product.getFiber());
							sodium += convertNutri(product.getSize(), product.getUnitSize(), product.getSodium());
							vitaminA += convertNutri(product.getSize(), product.getUnitSize(), product.getVitaminA());
							vitaminC += convertNutri(product.getSize(), product.getUnitSize(), product.getVitaminC());
							calcium += convertNutri(product.getSize(), product.getUnitSize(), product.getCalcium());
							iron += convertNutri(product.getSize(), product.getUnitSize(), product.getIron());
							switch (product.getFoodGroup().charAt(0)) {
				            case 'F':  fruit += sizeGr;
		                    	break;
				            case 'V':  vegetable += sizeGr;
		                     	break;
				            case 'M':  meat += sizeGr;
		                     	break;
				            case 'G':  grain += sizeGr;
		                     	break;
				            case 'D':  dairy += sizeGr;
		                     	break;
				            case 'C':  confection += sizeGr;
		                     	break;
				            case 'W':  water += sizeGr;
		                     	break;
				        }
						}
						out.print("<tr><td>" + product.getName() + " (" + product.getBrand() + ")</td><td>" + sizeVal + "</td></tr>");
					}
					out.print("</table>");
					out.print("<br/>Total nutritional data for " + totalSize + " g");
					out.print("<table width='500px'>");
					out.print("<tr><td>Calories:</td><td>" + calories + " kj</td><td width='40%'></td></tr>");
					out.print("<tr><td>Proteins:</td><td>" + proteins + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, proteins) + "' /></td><td>" + percentage(totalSize, proteins) + "%</td></tr>");
					out.print("<tr><td>Carbohydrates:</td><td>" + carbohydrates + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, carbohydrates) + "' /></td><td>" + percentage(totalSize, carbohydrates) + "%</td></tr>");
					out.print("<tr><td>Sugar:</td><td>" + sugar + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, sugar) + "' /></td><td>" + percentage(totalSize, sugar) + "%</td></tr>");
					out.print("<tr><td>Fat:</td><td>" + fat + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, fat) + "' /></td><td>" + percentage(totalSize, fat) + "%</td></tr>");
					out.print("<tr><td>Saturated Fat:</td><td>" + saturatedFat + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, saturatedFat) + "' /></td><td>" + percentage(totalSize, saturatedFat) + "%</td></tr>");
					out.print("<tr><td>Cholesterol:</td><td>" + cholesterol + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, cholesterol) + "' /></td><td>" + percentage(totalSize, cholesterol) + "%</td></tr>");
					out.print("<tr><td>Fiber:</td><td>" + fiber + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, fiber) + "' /></td><td>" + percentage(totalSize, fiber) + "%</td></tr>");
					out.print("<tr><td>Sodium:</td><td>" + sodium + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, sodium) + "' /></td><td>" + percentage(totalSize, sodium) + "%</td></tr>");
					out.print("<tr><td>Vitamin A:</td><td>" + vitaminA + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, vitaminA) + "' /></td><td>" + percentage(totalSize, vitaminA) + "%</td></tr>");
					out.print("<tr><td>Vitamin C:</td><td>" + vitaminC + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, vitaminC) + "' /></td><td>" + percentage(totalSize, vitaminC) + "%</td></tr>");
					out.print("<tr><td>Calcium:</td><td>" + calcium + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, calcium) + "' /></td><td>" + percentage(totalSize, calcium) + "%</td></tr>");
					out.print("<tr><td>Iron:</td><td>" + iron + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, iron) + "' /></td><td>" + percentage(totalSize, iron) + "%</td></tr>");
					out.print("</table><br/>");
					out.print("<table width='500px'>");
					out.print("<tr><td>Fruit:</td><td width='60%'><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, fruit) + "' /></td><td>" + percentage(totalSize, fruit) + "%</td></tr>");
					out.print("<tr><td>Vegetable:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, vegetable) + "' /></td><td>" + percentage(totalSize, vegetable) + "%</td></tr>");
					out.print("<tr><td>Meat:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, meat) + "' /></td><td>" + percentage(totalSize, meat) + "%</td></tr>");
					out.print("<tr><td>Grains, beans and legumes:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, grain) + "' /></td><td>" + percentage(totalSize, grain) + "%</td></tr>");
					out.print("<tr><td>Dairy:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, dairy) + "' /></td><td>" + percentage(totalSize, dairy) + "%</td></tr>");
					out.print("<tr><td>Confections:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, confection) + "' /></td><td>" + percentage(totalSize, confection) + "%</td></tr>");
					out.print("<tr><td>Water:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, water) + "' /></td><td>" + percentage(totalSize, water) + "%</td></tr>");
					out.print("</table>");
				} else {
					out.print("product is not found or the barcode is incorrect");
				}
			} %>
			<%!
				public double convertSize(double size, String unit){
					double result = size;
					if (unit.equals("kg") || unit.equals("ltr")) {
						result *=  1000;
					} else if (unit.equals("cl")) {
						result *=  10;
					} else if (unit.equals("lbs")) {
						result *=  453.592;
					}
					return roundTo2Decimals(result);
				}
				public double convertNutri(double size, String unit, double nutri){
					if (nutri == -1) {
						nutri = 0;
					}
					double result = size * nutri;
					if (unit.equals("kg") || unit.equals("ltr")) {
						result *=  10;
					} else if (unit.equals("cl")) {
						result *=  0.1;
					} else if (unit.equals("lbs")) {
						result *=  4.53592;
					} else {
						result *= 0.01;
					}
					return roundTo2Decimals(result);
				}
				
				public double percentage(double size, double nutri){
					return roundTo2Decimals((nutri * 100.0) / size);
				}
				
				public double roundTo2Decimals(double val) {
			        DecimalFormat df2 = new DecimalFormat("###.##");
			        return Double.valueOf(df2.format(val));
				}
				
			%>
	</div>
	
	<script type="text/javascript">
		$("#getNutrition").validate();
		
		function addProduct() {
			$("[name='barcode_div']").last().after('<div name="barcode_div">product barcode: <input type="digits" name="barcode" required /></div>');
		}
		
		$(function() {
			$("[name='nutriVal']").hide();
			$("[name='progressbar']").progressbar({
		        value: 0
			});
			$("[name='progressbar']").each(function() {
	            myNewValue = parseInt($(this).siblings("[name='nutriVal']").val());
	            $(this).progressbar("option", "value", myNewValue);
	        });
		    $(".ui-progressbar").height("20px");
		  });
	</script>
</body>
</html>