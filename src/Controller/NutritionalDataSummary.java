package Controller;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

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
			
			String text = "";
			text += "<br/><div id='summary'><table id='table1'>";
			text += "<tr><td><b>Product Name</b></td><td><b>Quantity</b></td></tr>";
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
				text += "<tr><td>" + product.getName() + " (" + product.getBrand() + ")</td><td>" + sizeVal + "</td></tr>";
			}
			text += "</table>";
			text += "<br/>Total nutritional data for " + totalSize + " g";
			text += "<table id='table2'>";
			text += "<tr><td>Calories:</td><td>" + calories + " kj</td><td id='cell1'>&nbsp;</td><td>&nbsp;</td></tr>";
			text += "<tr><td>Proteins:</td><td>" + proteins + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, proteins) + "' /></td><td>" + percentage(totalSize, proteins) + "%</td></tr>";
			text += "<tr><td>Carbohydrates:</td><td>" + carbohydrates + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, carbohydrates) + "' /></td><td>" + percentage(totalSize, carbohydrates) + "%</td></tr>";
			text += "<tr><td>Sugar:</td><td>" + sugar + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, sugar) + "' /></td><td>" + percentage(totalSize, sugar) + "%</td></tr>";
			text += "<tr><td>Fat:</td><td>" + fat + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, fat) + "' /></td><td>" + percentage(totalSize, fat) + "%</td></tr>";
			text += "<tr><td>Saturated Fat:</td><td>" + saturatedFat + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, saturatedFat) + "' /></td><td>" + percentage(totalSize, saturatedFat) + "%</td></tr>";
			text += "<tr><td>Cholesterol:</td><td>" + cholesterol + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, cholesterol) + "' /></td><td>" + percentage(totalSize, cholesterol) + "%</td></tr>";
			text += "<tr><td>Fiber:</td><td>" + fiber + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, fiber) + "' /></td><td>" + percentage(totalSize, fiber) + "%</td></tr>";
			text += "<tr><td>Sodium:</td><td>" + sodium + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, sodium) + "' /></td><td>" + percentage(totalSize, sodium) + "%</td></tr>";
			text += "<tr><td>Vitamin A:</td><td>" + vitaminA + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, vitaminA) + "' /></td><td>" + percentage(totalSize, vitaminA) + "%</td></tr>";
			text += "<tr><td>Vitamin C:</td><td>" + vitaminC + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, vitaminC) + "' /></td><td>" + percentage(totalSize, vitaminC) + "%</td></tr>";
			text += "<tr><td>Calcium:</td><td>" + calcium + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, calcium) + "' /></td><td>" + percentage(totalSize, calcium) + "%</td></tr>";
			text += "<tr><td>Iron:</td><td>" + iron + " g</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, iron) + "' /></td><td>" + percentage(totalSize, iron) + "%</td></tr>";
			text += "</table><br/>";
			text += "<table id='table3'>";
			text += "<tr><td>Fruit:</td><td id='cell2'><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, fruit) + "' /></td><td>" + percentage(totalSize, fruit) + "%</td></tr>";
			text += "<tr><td>Vegetable:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, vegetable) + "' /></td><td>" + percentage(totalSize, vegetable) + "%</td></tr>";
			text += "<tr><td>Meat:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, meat) + "' /></td><td>" + percentage(totalSize, meat) + "%</td></tr>";
			text += "<tr><td>Grains, beans and legumes:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, grain) + "' /></td><td>" + percentage(totalSize, grain) + "%</td></tr>";
			text += "<tr><td>Dairy:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, dairy) + "' /></td><td>" + percentage(totalSize, dairy) + "%</td></tr>";
			text += "<tr><td>Confections:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, confection) + "' /></td><td>" + percentage(totalSize, confection) + "%</td></tr>";
			text += "<tr><td>Water:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, water) + "' /></td><td>" + percentage(totalSize, water) + "%</td></tr>";
			text += "</table></div>";
			
			try {
				Document document = new Document(PageSize.LETTER);
				PdfWriter.getInstance(document,
						new FileOutputStream("/Users/vamhan/Downloads/apache-tomcat-7.0.52/webapps/ROOT/Nutrition/WebContent/testpdf1.pdf"));
				document.open();

				HTMLWorker htmlWorker = new HTMLWorker(document);
				htmlWorker.parse(new StringReader(text));
				document.close();
				System.out.println("Done");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			request.setAttribute("data", text);
			getServletConfig().getServletContext()
					.getRequestDispatcher("/summary.jsp")
					.forward(request, response);

		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
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
}
