<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="jquery/jquery.validate.js"></script>
<title>Insert title here</title>
</head>
<body>
	<form id="addNutrition" action="/Nutrition/RetrieveNutritionalData" method="post" >
		<table>
			<tr><td>Product Barcode:</td><td><input id="barcode" type="digits" name="barcode" required></td></tr>
			<tr><td>Product Name:</td><td><input type="text" name="name" required></td></tr>
			<tr><td>Size:</td><td><input type="number" name="size"></td></tr>
			<tr><td>Unit Size:</td><td>
				<select name="unit">
				  <option value="gr">gr</option>
				  <option value="kg">kg</option>
				  <option value="ml">ml</option>
				  <option value="ltr">ltr</option>
				  <option value="cl">cl</option>
				  <option value="lbs">lbs</option>
				</select>
			</td></tr>
			<tr><td>Brand:</td><td><input type="text" name="brand"></td></tr>
			<tr><td>Food Group:</td><td>
				<select name="group">
				  <option value="1">fruit</option>
				  <option value="2">vegetable</option>
				  <option value="3">meat</option>
				  <option value="4">dairy</option>
				  <option value="5">Grains, beans and legumes</option>
				  <option value="6">confections</option>
				  <option value="7">water</option>
				</select>
			</td></tr>
			<tr><td>Nutritional data (per 100g)</td><td></td></tr>
			<tr><td>Calories:</td><td><input type="number" name="calory"> kj</td></tr>
			<tr><td>Proteins:</td><td><input type="number" name="protein"> g</td></tr>
			<tr><td>Carbohydrates:</td><td><input type="number" name="carb"> g</td></tr>
			<tr><td>Sugar:</td><td><input type="number" name="sugar"> g</td></tr>
			<tr><td>Fat:</td><td><input type="number" name="fat"> g</td></tr>
			<tr><td>Saturated Fat:</td><td><input type="number" name="sat_fat"> g</td></tr>
			<tr><td>Cholesterol:</td><td><input type="number" name="choles"> g</td></tr>
			<tr><td>Fiber:</td><td><input type="number" name="fiber"> g</td></tr>
			<tr><td>Sodium:</td><td><input type="number" name="sodium"> g</td></tr>
			<tr><td>vitamin A:</td><td><input type="number" name="vita"> g</td></tr>
			<tr><td>vitamin C:</td><td><input type="number" name="vitc"> g</td></tr>
			<tr><td>Calcium:</td><td><input type="number" name="calcium"> g</td></tr>
			<tr><td>Iron:</td><td><input type="number" name="iron"> g</td></tr>
		</table>
		<input type="button" value="Add" onclick="checkDupId();">
		<input type="button" value="Cancel" onclick="window.location.replace('index.jsp');">
	</form>
	
	
	<script type="text/javascript">
		$("#addNutrition").validate();
		$(function() {
			if ($("#status").val() == "0") {
				alert("An error occurred. Please try again later");
			}
		});
		
		function checkDupId() {
			$.get("/Nutrition/RetrieveNutritionalData", {
				barcode : $("#barcode").val(),
				check : ""
			}).done(function(result) {
				if (result == "1") {
					alert("This product barcode is already exist!");
				} else {
					$("#addNutrition").submit();
				}
			})
		}
	</script>
</body>
</html>