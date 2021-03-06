<%@page import="sun.text.normalizer.CharTrie.FriendAgent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript" src="jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="jquery/jquery.validate.js"></script>
<script type="text/javascript" src="jquery/jquery-ui-1.8.20.custom.min.js"></script>
<script type="text/javascript" src="jquery/jquery.fileDownload.js"></script>
<link rel="stylesheet" type="text/css" href="jquery/jquery-ui-1.8.23.custom.css" />
<title>Insert title here</title>
</head>
<body>
	<form id="getNutrition" action="/Nutrition/NutritionalDataSummary" method="get">
		<div>userID: <input type="digits" name="userID" required /></div>
		<div name="barcode_div">product barcode: <input type="digits" name="barcode" required /> amount: <input type="number" name="amount" value="1" style="width:50px" required /></div>
		<input type="button" value="Submit" onclick="validate();">
		<input type="button" value="Add product" onclick="addProduct();">
	</form>
	
	<div><% if (request.getParameter("barcode") != null) {
				if (request.getAttribute("data") != null) {
					String text = (String)request.getAttribute("data");
					
					out.print(text);
				} else {
					out.print("product is not found or the barcode is incorrect");
				}
			} %>
	</div>
	
	<script type="text/javascript">
		//$("#getNutrition").validate();
		
		function addProduct() {
			$("[name='barcode_div']").last().after('<div name="barcode_div">product barcode: <input type="digits" name="barcode" required /> amount: <input type="number" name="amount" value="1" style="width:50px" required /></div>');
		}
		
		function validate() {
			var flag = true;
			if ($("[name='userID']").val().match(/^\d+$/)) {
				$("[name='barcode']").each(function() {
					if (!$(this).val().match(/^\d+$/)) {
						flag = false;
					}
				});
				if (flag) {
					$("[name='amount']").each(function() {
						if (!$(this).val().match(/^\d+$/)) {
							flag = false;
						}
					});
					if (flag) {
						$("form").submit();
					} else {
						alert("amount is required and accepted only digits");
					}
				} else {
					alert("barcode is required and accepted only digits");
				}
			} else {
				alert("userID is required and accepted only digits");
			}
		}
		
		$(function() {
			$("[name='nutriVal']").hide();
			$("[name='progressbarN']").progressbar({
				max: <% out.print((String)request.getAttribute("maxN")); %>,
		        value: 0
			});
			$("[name='progressbar']").progressbar({
				max: <% out.print((String)request.getAttribute("max")); %>,
		        value: 0
			});
			$(".ui-progressbar").each(function() {
	            myNewValue = parseFloat($(this).siblings("[name='nutriVal']").val());
	            $(this).progressbar("option", "value", myNewValue);
	        });
		    $(".ui-progressbar").height("20px");
		    
		    $("#table1").width("600px");
		    $("#table2").width("500px");
		    $("#table3").width("500px");
		    $("#cell1").width("40%");
		    $("#cell2").width("60%");

		    /*var doc = new jsPDF();
	        var specialElementHandlers = {
	            '#editor': function (element,renderer) {
	                return true;
	            }
	        };
            doc.fromHTML($('#summary').html(), 15, 15, {
                'width': 170,'elementHandlers': specialElementHandlers
            });
            doc.save('sample-file.pdf');*/
		  });
	</script>
</body>
</html>