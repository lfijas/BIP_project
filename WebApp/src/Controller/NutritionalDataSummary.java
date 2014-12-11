package Controller;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import Model.Brand;
import Model.Product;
import Model.PurchaseHistory;
import Model.User;

public class NutritionalDataSummary extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		//response.setCharacterEncoding( "UTF-8" );
		
		try {
			String userID = request.getParameter("userID");
			String[] barcodes=request.getParameterValues("barcode");
			String[] amounts=request.getParameterValues("amount");
			Map<String, Integer> productList = new HashMap<String, Integer>();
			for (int i = 0; i < barcodes.length; i++) {
				int amount = 0;
				if (productList.get(barcodes[i]) != null) {
					amount = productList.get(barcodes[i]);
				}
				productList.put(barcodes[i], amount + Integer.parseInt(amounts[i]));
			}
			System.out.println(productList.toString());
			List<Product> products = Product.getProductsByMultipleBarcodes(productList.keySet().toArray(new String[productList.keySet().size()]));
			//Product summary = Product.getNutritionalSummary(barcodes);
			
			String output = "";
			double maxN = 0;
			double max = 0;
			double totalSize = 0;
			if (!User.isUserIDExist(userID)) {
				output = "userID not found";
			} else if (products.size() < productList.keySet().size()) {
				output = "some products are not found or the barcodes are incorrect!";
			} else {
				String text = "";
				text += "<br/><div id='summary'><table id='table1'>";
				text += "<tr><td><b>Product Name</b></td><td><b>Quantity(1 unit)</b></td><td><b>Amount</b></td></tr>";
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
				for (int i = 0; i < products.size(); i++) {
					String sizeVal = "-";
					Product product = products.get(i);
					System.out.println(product.getCode());
					int amount = productList.get(product.getCode());
					if (product.getSize() >= 0) {
						sizeVal = (product.getSize()) + " " + product.getUnitSize();
						double sizeGr = convertSize(product.getSize() * amount, product.getUnitSize());
						totalSize += sizeGr;
						calories += convertNutri(product.getSize(), product.getUnitSize(), product.getCalories()) * amount;
						proteins += convertNutri(product.getSize(), product.getUnitSize(), product.getProteins()) * amount;
						carbohydrates += convertNutri(product.getSize(), product.getUnitSize(), product.getCarbohydrates()) * amount;
						sugar += convertNutri(product.getSize(), product.getUnitSize(), product.getSugar()) * amount;
						fat += convertNutri(product.getSize(), product.getUnitSize(), product.getFat()) * amount;
						saturatedFat += convertNutri(product.getSize(), product.getUnitSize(), product.getSaturatedFat()) * amount;
						cholesterol += convertNutri(product.getSize(), product.getUnitSize(), product.getCholesterol()) * amount;
						fiber += convertNutri(product.getSize(), product.getUnitSize(), product.getFiber()) * amount;
						sodium += convertNutri(product.getSize(), product.getUnitSize(), product.getSodium()) * amount;
						vitaminA += convertNutri(product.getSize(), product.getUnitSize(), product.getVitaminA()) * amount;
						vitaminC += convertNutri(product.getSize(), product.getUnitSize(), product.getVitaminC()) * amount;
						calcium += convertNutri(product.getSize(), product.getUnitSize(), product.getCalcium()) * amount;
						iron += convertNutri(product.getSize(), product.getUnitSize(), product.getIron()) * amount;
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
					text += "<tr><td width='70%'>" + product.getName() + " (" + product.getBrand() + ")</td><td width='20%'>" + sizeVal + "</td><td>" + amount + "</td></tr>";
				}
				
				text += "</table>";
				text += "<br/>Total nutritional data for " + totalSize + " g";
				text += "<table>";
				text += "<tr><td>Calories:</td><td>" + calories + " kj</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
				text += "</table>";
				
				output = text;
				output += "<table id='table2'>";
				DefaultCategoryDataset dataSetN = new DefaultCategoryDataset();
				int countRow = 0;
				if (proteins > 0) {
					dataSetN.setValue(percentage(totalSize, proteins), "Food Group", "Proteins");
					maxN = proteins > maxN ? proteins : maxN;
					output += "<tr><td>Proteins:</td><td>" + proteins + " g</td><td id='cell1'><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, proteins) + "' /></td><td>" + percentage(totalSize, proteins) + "%</td></tr>";
					countRow++;
				}
				if (carbohydrates > 0) {
					dataSetN.setValue(percentage(totalSize, carbohydrates), "Food Group", "Carbohydrates");
					maxN = carbohydrates > maxN ? carbohydrates : maxN;
					output += "<tr><td>Carbohydrates:</td><td>" + carbohydrates + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, carbohydrates) + "' /></td><td>" + percentage(totalSize, carbohydrates) + "%</td></tr>";
					countRow++;
				}
				if (sugar > 0) {
					dataSetN.setValue(percentage(totalSize, sugar), "Food Group", "Sugar");
					maxN = sugar > maxN ? sugar : maxN;
					output += "<tr><td>Sugar:</td><td>" + sugar + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, sugar) + "' /></td><td>" + percentage(totalSize, sugar) + "%</td></tr>";
					countRow++;
				}
				if (fat > 0) {
					dataSetN.setValue(percentage(totalSize, fat), "Food Group", "Fat");
					maxN = fat > maxN ? fat : maxN;
					output += "<tr><td>Fat:</td><td>" + fat + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, fat) + "' /></td><td>" + percentage(totalSize, fat) + "%</td></tr>";
					countRow++;
				}
				if (saturatedFat > 0) {
					dataSetN.setValue(percentage(totalSize, saturatedFat), "Food Group", "Saturated Fat");
					maxN = saturatedFat > maxN ? saturatedFat : maxN;
					output += "<tr><td>Saturated Fat:</td><td>" + saturatedFat + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, saturatedFat) + "' /></td><td>" + percentage(totalSize, saturatedFat) + "%</td></tr>";
					countRow++;
				}
				if (cholesterol > 0) {
					dataSetN.setValue(percentage(totalSize, cholesterol), "Food Group", "Cholesterol");
					maxN = cholesterol > maxN ? cholesterol : maxN;
					output += "<tr><td>Cholesterol:</td><td>" + cholesterol + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, cholesterol) + "' /></td><td>" + percentage(totalSize, cholesterol) + "%</td></tr>";
					countRow++;
				}
				if (fiber > 0) {
					dataSetN.setValue(percentage(totalSize, fiber), "Food Group", "Fiber");
					maxN = fiber > maxN ? fiber : maxN;
					output += "<tr><td>Fiber:</td><td>" + fiber + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, fiber) + "' /></td><td>" + percentage(totalSize, fiber) + "%</td></tr>";
					countRow++;
				}
				if (sodium > 0) {
					dataSetN.setValue(percentage(totalSize, sodium), "Food Group", "Sodium");
					maxN = sodium > maxN ? sodium : maxN;
					output += "<tr><td>Sodium:</td><td>" + sodium + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, sodium) + "' /></td><td>" + percentage(totalSize, sodium) + "%</td></tr>";
					countRow++;
				}
				if (vitaminA > 0) {
					dataSetN.setValue(percentage(totalSize, vitaminA), "Food Group", "Vitamin A");
					maxN = vitaminA > maxN ? vitaminA : maxN;
					output += "<tr><td>Vitamin A:</td><td>" + vitaminA + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, vitaminA) + "' /></td><td>" + percentage(totalSize, vitaminA) + "%</td></tr>";
					countRow++;
				}
				if (vitaminC > 0) {
					dataSetN.setValue(percentage(totalSize, vitaminC), "Food Group", "Vitamin C");
					maxN = vitaminC > maxN ? vitaminC : maxN;
					output += "<tr><td>Vitamin C:</td><td>" + vitaminC + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, vitaminC) + "' /></td><td>" + percentage(totalSize, vitaminC) + "%</td></tr>";
					countRow++;
				}
				if (calcium > 0) {
					dataSetN.setValue(percentage(totalSize, calcium), "Food Group", "Calcium");
					maxN = calcium > maxN ? calcium : maxN;
					output += "<tr><td>Calcium:</td><td>" + calcium + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, calcium) + "' /></td><td>" + percentage(totalSize, calcium) + "%</td></tr>";
					countRow++;
				}
				if (iron > 0) {
					dataSetN.setValue(percentage(totalSize, iron), "Food Group", "Iron");
					maxN = iron > maxN ? iron : maxN;
					output += "<tr><td>Iron:</td><td>" + iron + " g</td><td><div name='progressbarN'></div><input name='nutriVal' value='" + percentage(totalSize, iron) + "' /></td><td>" + percentage(totalSize, iron) + "%</td></tr>";
					countRow++;
				}
				output += "</table><br/>";

				output += "<table id='table3'>";
				output += "<tr><td>Fruit:</td><td id='cell2'><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, fruit) + "' /></td><td>" + percentage(totalSize, fruit) + "%</td></tr>";
				output += "<tr><td>Vegetable:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, vegetable) + "' /></td><td>" + percentage(totalSize, vegetable) + "%</td></tr>";
				output += "<tr><td>Meat:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, meat) + "' /></td><td>" + percentage(totalSize, meat) + "%</td></tr>";
				output += "<tr><td>Grains, beans and legumes:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, grain) + "' /></td><td>" + percentage(totalSize, grain) + "%</td></tr>";
				output += "<tr><td>Dairy:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, dairy) + "' /></td><td>" + percentage(totalSize, dairy) + "%</td></tr>";
				output += "<tr><td>Confections:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, confection) + "' /></td><td>" + percentage(totalSize, confection) + "%</td></tr>";
				output += "<tr><td>Water:</td><td><div name='progressbar'></div><input name='nutriVal' value='" + percentage(totalSize, water) + "' /></td><td>" + percentage(totalSize, water) + "%</td></tr>";
				output += "</table></div>";
				output += "<a href='download.jsp'><input id='download' type='button' value='Print as PDF'/></a>";
				
				try {
					Document document = new Document(PageSize.LETTER);
					String path = "C:\\testpdf1.pdf";
					String OS = System.getProperty("os.name").toLowerCase();
					if (OS.indexOf("win") >= 0) {
						path.replace('/', '\\');
					}
					PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
					document.open();
	
					HTMLWorker htmlWorker = new HTMLWorker(document);
					htmlWorker.parse(new StringReader(text));
					
					//generate nutrition bar chart
			        JFreeChart chartN = ChartFactory.createBarChart(
			                "Total Nutrition Percentage", "Nutritional Data", "Percentage (compare with total size)",
			                dataSetN, PlotOrientation.HORIZONTAL, false, true, false);
					
					writeChartToPDF(chartN, 500, (20 * countRow) + 100, writer, document, percentage(totalSize, maxN), false);
					
					//generate food group bar chart
					DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
			        dataSet.setValue(percentage(totalSize, fruit), "Food Group", "fruit");
			        dataSet.setValue(percentage(totalSize, vegetable), "Food Group", "vegetable");
			        dataSet.setValue(percentage(totalSize, meat), "Food Group", "meat");
			        dataSet.setValue(percentage(totalSize, grain), "Food Group", "grains, beans and legumes");
			        dataSet.setValue(percentage(totalSize, dairy), "Food Group", "dairy");
			        dataSet.setValue(percentage(totalSize, confection), "Food Group", "confections");
			        dataSet.setValue(percentage(totalSize, water), "Food Group", "water");
			 
			        JFreeChart chart = ChartFactory.createBarChart(
			                "Total Food Group Percentage", "Food Group", "Percentage (compare with total size)",
			                dataSet, PlotOrientation.HORIZONTAL, false, true, false);
			        max = Math.max(fruit, Math.max(vegetable, Math.max(meat, Math.max(grain, Math.max(dairy, Math.max(confection, water))))));
			        
					
					writeChartToPDF(chart, 500, 250, writer, document, percentage(totalSize, max), true);
					document.close();
					System.out.println("Done");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				//add purchase history
				PurchaseHistory purchase = new PurchaseHistory();
				purchase.setUserID(Integer.parseInt(userID));
				purchase.setBranchID(1);
				Calendar calendar = Calendar.getInstance();
				purchase.setDatetime(new java.sql.Timestamp(calendar.getTime().getTime()));
				purchase.setProductList(productList);
				PurchaseHistory.createPurchase(purchase);
			}
			
			request.setAttribute("data", output);
			request.setAttribute("maxN", percentage(totalSize, maxN) + "");
			request.setAttribute("max", percentage(totalSize, max) + "");
			getServletConfig().getServletContext()
					.getRequestDispatcher("/summary.jsp")
					.forward(request, response);

		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	class CustomRenderer extends BarRenderer {

        private Paint[] colors;

        public CustomRenderer(final Paint[] colors) {
            this.colors = colors;
        }

        public Paint getItemPaint(final int row, final int column) {
            return this.colors[column % this.colors.length];
        }
    }

	
	public void writeChartToPDF(JFreeChart chart, int width, int height, PdfWriter writer, Document document, double max, boolean color) {
	 
	    try {
	        /*PdfContentByte contentByte = writer.getDirectContent();
	        PdfTemplate template = contentByte.createTemplate(width, height);
	        Graphics2D graphics2d = template.createGraphics(width, height,
	                new DefaultFontMapper());
	        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
	                height);
	 
	        chart.draw(graphics2d, rectangle2d);
	         
	        graphics2d.dispose();
	        contentByte.addTemplate(template, 0, 0);*/
	    	
	        CategoryPlot plot = chart.getCategoryPlot();
	        Font font3 = new Font("Dialog", Font.PLAIN, 12); 
	        chart.getTitle().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
	        plot.getDomainAxis().setLabelFont(font3);
	        plot.getRangeAxis().setLabelFont(font3);
	        
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        rangeAxis.setRange(0.00, max + (max / 7.0));

	        // or rangeAxis.setMaximumAxisValue(100.0);
	        
	        CategoryItemRenderer renderer;
	        if (color) {
		        renderer = new CustomRenderer(
	                new Paint[] {Color.magenta, Color.blue, Color.cyan, Color.green,
	                    Color.yellow, Color.red,
	                    Color.pink}
	            );
	        } else {
	        	renderer = new CustomRenderer(
		                new Paint[] {new Color(255, 128, 0)}
		            );
	        }
	        
	        DecimalFormat decimalformat1 = new DecimalFormat("##.##'%'");
	        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1));
	        //renderer.setItemLabelsVisible(true);
	        renderer.setBaseItemLabelsVisible(true);
	        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT));
            plot.setRenderer(renderer);
            
	    	PdfContentByte pdfContentByte = writer.getDirectContent();
	    	PdfTemplate pdfTemplateChartHolder = pdfContentByte.createTemplate(width,height);
	    	Graphics2D graphicsChart = pdfTemplateChartHolder.createGraphics(width,height,new DefaultFontMapper());
	    	Rectangle2D chartRegion = new Rectangle2D.Double(0,0,width,height);
	    	chart.draw(graphicsChart,chartRegion);
	    	graphicsChart.dispose();

	    	Image chartImage = Image.getInstance(pdfTemplateChartHolder);
	    	document.add(chartImage);
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private double convertSize(double size, String unit){
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
	private double convertNutri(double size, String unit, double nutri){
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
	
	private double percentage(double size, double nutri){
		if (size != 0) {
			return roundTo2Decimals((nutri * 100.0) / size);
		} else {
			return 0;
		}
	}
	
	private double roundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
	}
}
