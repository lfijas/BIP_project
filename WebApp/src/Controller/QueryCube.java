package Controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.layout.RectangularCellSetFormatter;
import org.olap4j.metadata.Member;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import Controller.NutritionalDataSummary.CustomRenderer;
import Model.DBConnector;
import Model.GroupSummary;
import Model.Summary;

/**
 * Collection of olap4j samples illustrating connections and statements.
 * 
 * @author jhyde
 * @version $Id: SimpleQuerySample.java 125 2008-11-02 07:43:12Z jhyde $
 * @since Aug 22, 2006
 */
public class QueryCube {
	public static void main(String[] args) {
		try {
			
			// Retrieve all Purchases for a specific user for a specific range of date (set dateRange to null is no specific date range)
			List<Summary> sumList = sumNutritionGroupByPurchase(1, null);
			System.out.println(sumList.get(0).getTitle());
			System.out.println(sumList.get(0).getSize());
			System.out.println(sumList.get(0).getCalories());
			System.out.println(sumList.get(0).getVitaminC());
			System.out.println(sumList.get(1).getTitle());
			System.out.println(sumList.get(1).getSize());
			
			List<String> dateRange = new ArrayList<>();
			dateRange.add("26/11/2014");
			dateRange.add("27/11/2014");
			sumNutritionGroupByPurchase(1, dateRange);
			
			// Retrieve all default categories for a specific user for a specific range of date
			sumNutritionGroupByCategory(1, null);
			List<Summary> sumList2 = sumNutritionGroupByCategory(1, dateRange);
			System.out.println(sumList2.get(0).getTitle());
			System.out.println(sumList2.get(0).getSize());
			
			// Retrieve all product in specific category for a specific user for a specific range of date
			List<Summary> sumList3 = sumNutritionOfCategory(1, "Confections", null);
			System.out.println(sumList3.get(0).getTitle());
			System.out.println(sumList3.get(0).getSize());
			sumNutritionOfCategory(1, "Confections", dateRange);
			
			// Retrieve all product in specific custom category for a specific user for a specific range of date
			// you have to query list of product name of that custom category first and pass productID list as parameter
			List<String> prods = new ArrayList<>();
			prods.add("Dark chocolate &quot;Bellarom&quot; 74% cocoa");
			List<Summary> sumList4 = sumNutritionOfCustomCategory(1, prods, null);
			System.out.println(sumList4.get(0).getTitle());
			System.out.println(sumList4.get(0).getSize());
			sumNutritionOfCustomCategory(1, prods, dateRange);
			
			// Retrieve default category summary
			GroupSummary sumList5 = sumCategory(1, null);
			System.out.println(sumList5.getConfections());
			System.out.println(sumList5.getDairy());
			System.out.println(sumList5.getFruit());
			
			
			Document document = new Document(PageSize.LETTER);
			String path = "/Users/vamhan/Desktop/a.pdf";
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();
			
			// Retrieve graph for nutrition summary (pass Summary object as a parameter)
			JFreeChart chart = generateNutritionChart(sumList.get(0));
			
			PdfContentByte pdfContentByte = writer.getDirectContent();
	    	PdfTemplate pdfTemplateChartHolder = pdfContentByte.createTemplate(500, 200);
	    	Graphics2D graphicsChart = pdfTemplateChartHolder.createGraphics(500, 200,new DefaultFontMapper());
	    	Rectangle2D chartRegion = new Rectangle2D.Double(0,0,500,200);
	    	chart.draw(graphicsChart,chartRegion);
	    	graphicsChart.dispose();

	    	Image chartImage = Image.getInstance(pdfTemplateChartHolder);
	    	document.add(chartImage);
	    	
	    	// Retrieve graph for default category summary (pass GroupSummary object as a parameter)
	    	JFreeChart chart2 = generateFoodGroupChart(sumList5);
			
			PdfContentByte pdfContentByte2 = writer.getDirectContent();
	    	PdfTemplate pdfTemplateChartHolder2 = pdfContentByte2.createTemplate(500, 200);
	    	Graphics2D graphicsChart2 = pdfTemplateChartHolder2.createGraphics(500, 200,new DefaultFontMapper());
	    	Rectangle2D chartRegion2 = new Rectangle2D.Double(0,0,500,200);
	    	chart2.draw(graphicsChart2,chartRegion2);
	    	graphicsChart2.dispose();

	    	Image chartImage2 = Image.getInstance(pdfTemplateChartHolder2);
	    	document.add(chartImage2);
	    	
	    	document.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<Summary> sumNutritionGroupByPurchase(int userid, List<String> dateRange) throws ClassNotFoundException, SQLException {
		String dates = "[Date.Date Hierarchy].[DATE].Members";
		if (dateRange != null) {
			dates = "{[Date.Date Hierarchy].[DATE].[" + dateRange.get(0) + "]";
			for (int i = 1; i < dateRange.size(); i++) {
				dates += ", [Date.Date Hierarchy].[DATE].[" + dateRange.get(i) + "]";
			}
			dates += "}";
		}
		
		String query = "select {[Measures].[SUMsize], [Measures].[SUMcalories], [Measures].[SUMproteins], [Measures].[SUMcarbohydrates], [Measures].[SUMsugar], [Measures].[SUMfat],"
				+ "[Measures].[SUMsaturatedfat], [Measures].[SUMfiber], [Measures].[SUMchoresterol], [Measures].[SUMsodium], [Measures].[SUMcalcium]"
				+ ", [Measures].[SUMiron], [Measures].[SUMvitaminA], [Measures].[SUMvitaminC], [Measures].[SUMprice]} ON COLUMNS,"
				+ "NON EMPTY Crossjoin([Purchase].[All Purchases].Children, " + dates + ") ON ROWS "
				+ "from [Nutrition_Fact] "
				+ "where([Customer.Customer Hierarchy].[ID].[" + userid + "])";
		
		return query(query, 1);
	}
	
	public static List<Summary> sumNutritionGroupByAllPurchase(int userid, List<String> dateRange) throws ClassNotFoundException, SQLException {
		String dates = "";
		if (dateRange != null) {
			dates = "* {[Date.Date Hierarchy].[DATE].[" + dateRange.get(0) + "]";
			for (int i = 1; i < dateRange.size(); i++) {
				dates += ", [Date.Date Hierarchy].[DATE].[" + dateRange.get(i) + "]";
			}
			dates += "}";
		}
		
		String query = "select {[Measures].[SUMsize], [Measures].[SUMcalories], [Measures].[SUMproteins], [Measures].[SUMcarbohydrates], [Measures].[SUMsugar], [Measures].[SUMfat],"
				+ "[Measures].[SUMsaturatedfat], [Measures].[SUMfiber], [Measures].[SUMchoresterol], [Measures].[SUMsodium], [Measures].[SUMcalcium]"
				+ ", [Measures].[SUMiron], [Measures].[SUMvitaminA], [Measures].[SUMvitaminC], [Measures].[SUMprice]} ON COLUMNS,"
				+ "NON EMPTY [Purchase].[All Purchases] ON ROWS "
				+ "from [Nutrition_Fact] "
				+ "where([Customer.Customer Hierarchy].[ID].[" + userid + "]" + dates + ")";
		
		return query(query, 1);
	}
	
	public static List<Summary> sumNutritionGroupByCategory(int userid, List<String> dateRange) throws ClassNotFoundException, SQLException {
		String dates = "";
		if (dateRange != null) {
			dates = "* {[Date.Date Hierarchy].[DATE].[" + dateRange.get(0) + "]";
			for (int i = 1; i < dateRange.size(); i++) {
				dates += ", [Date.Date Hierarchy].[DATE].[" + dateRange.get(i) + "]";
			}
			dates += "}";
		}
		
		String query = "select {[Measures].[SUMsize], [Measures].[SUMcalories], [Measures].[SUMproteins], [Measures].[SUMcarbohydrates], [Measures].[SUMsugar], [Measures].[SUMfat],"
				+ "[Measures].[SUMsaturatedfat], [Measures].[SUMfiber], [Measures].[SUMchoresterol], [Measures].[SUMsodium], [Measures].[SUMcalcium]"
				+ ", [Measures].[SUMiron], [Measures].[SUMvitaminA], [Measures].[SUMvitaminC], [Measures].[SUMprice]} ON COLUMNS,"
				+ "NON EMPTY [Product].[All Categories].children ON ROWS "
				+ "from [Nutrition_Fact] "
				+ "where([Customer.Customer Hierarchy].[ID].[" + userid + "]" + dates + ")";
		
		return query(query, 1);
	}
	
	public static GroupSummary sumCategory(int userid, List<String> dateRange) throws ClassNotFoundException, SQLException {
		String dates = "";
		if (dateRange != null) {
			dates = "* {[Date.Date Hierarchy].[DATE].[" + dateRange.get(0) + "]";
			for (int i = 1; i < dateRange.size(); i++) {
				dates += ", [Date.Date Hierarchy].[DATE].[" + dateRange.get(i) + "]";
			}
			dates += "}";
		}
		
		String query = "select {[Measures].[SUMsize]} ON COLUMNS,"
				+ "NON EMPTY [Product].[All Categories].children ON ROWS "
				+ "from [Nutrition_Fact] "
				+ "where([Customer.Customer Hierarchy].[ID].[" + userid + "]" + dates + ")";
		
		return queryGroup(query, 1);
	}
	
	
	public static List<Summary> sumNutritionOfCategory(int userid, String category, List<String> dateRange) throws ClassNotFoundException, SQLException {
		String dates = "";
		if (dateRange != null) {
			dates = "* {[Date.Date Hierarchy].[DATE].[" + dateRange.get(0) + "]";
			for (int i = 1; i < dateRange.size(); i++) {
				dates += ", [Date.Date Hierarchy].[DATE].[" + dateRange.get(i) + "]";
			}
			dates += "}";
		}
		
		String query = "select {[Measures].[SUMsize], [Measures].[SUMcalories], [Measures].[SUMproteins], [Measures].[SUMcarbohydrates], [Measures].[SUMsugar], [Measures].[SUMfat],"
				+ "[Measures].[SUMsaturatedfat], [Measures].[SUMfiber], [Measures].[SUMchoresterol], [Measures].[SUMsodium], [Measures].[SUMcalcium]"
				+ ", [Measures].[SUMiron], [Measures].[SUMvitaminA], [Measures].[SUMvitaminC], [Measures].[SUMprice]} ON COLUMNS,"
				+ "NON EMPTY [Product.Category Hierarchy].[CATEGORY].[" + category + "].children ON ROWS "
				+ "from [Nutrition_Fact] "
				+ "where([Customer.Customer Hierarchy].[ID].[" + userid + "]" + dates + ")";
		
		return query(query, 1);
	}
	
	public static List<Summary> sumNutritionOfCustomCategory(int userid, List<String> productIDs, List<String> dateRange) throws ClassNotFoundException, SQLException {
		String dates = "";
		if (dateRange != null) {
			dates = "* {[Date.Date Hierarchy].[DATE].[" + dateRange.get(0) + "]";
			for (int i = 1; i < dateRange.size(); i++) {
				dates += ", [Date.Date Hierarchy].[DATE].[" + dateRange.get(i) + "]";
			}
			dates += "}";
		}
		
		String prods = "{[Product.Product Hierarchy].[PRODUCT].[" + productIDs.get(0) + "]";
		for (int i = 1; i < productIDs.size(); i++) {
			prods += ", [Product.Product Hierarchy].[PRODUCT].[" + productIDs.get(i) + "]";
		}
		prods += "}";
		
		String query = "select {[Measures].[SUMsize], [Measures].[SUMcalories], [Measures].[SUMproteins], [Measures].[SUMcarbohydrates], [Measures].[SUMsugar], [Measures].[SUMfat],"
				+ "[Measures].[SUMsaturatedfat], [Measures].[SUMfiber], [Measures].[SUMchoresterol], [Measures].[SUMsodium], [Measures].[SUMcalcium]"
				+ ", [Measures].[SUMiron], [Measures].[SUMvitaminA], [Measures].[SUMvitaminC], [Measures].[SUMprice]} ON COLUMNS,"
				+ "NON EMPTY " + prods + " ON ROWS "
				+ "from [Nutrition_Fact] "
				+ "where([Customer.Customer Hierarchy].[ID].[" + userid + "]" + dates + ")";
		
		return query(query, 1);
	}
	
	public static JFreeChart generateNutritionChart(Summary summary) {
		JFreeChart chart = null;
		DefaultCategoryDataset dataSetN = new DefaultCategoryDataset();
		int countRow = 0;
		double maxN = 0;
		if (summary.getProteins() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getProteins()), "", "Proteins");
			maxN = summary.getProteins() > maxN ? summary.getProteins() : maxN;
			countRow++;
		}
		if (summary.getCarbohydrates() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getCarbohydrates()), "", "Carbohydrates");
			maxN = summary.getCarbohydrates() > maxN ? summary.getCarbohydrates() : maxN;
			countRow++;
		}
		if (summary.getSugar() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getSugar()), "", "Sugar");
			maxN = summary.getSugar() > maxN ? summary.getSugar() : maxN;
			countRow++;
		}
		if (summary.getFat() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getFat()), "", "Fat");
			maxN = summary.getFat() > maxN ? summary.getFat() : maxN;
			countRow++;
		}
		if (summary.getSaturatedFat() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getSaturatedFat()), "Food Group", "Saturated Fat");
			maxN = summary.getSaturatedFat() > maxN ? summary.getSaturatedFat() : maxN;
			countRow++;
		}
		if (summary.getCholesterol() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getCholesterol()), "Food Group", "Cholesterol");
			maxN = summary.getCholesterol() > maxN ? summary.getCholesterol() : maxN;
			countRow++;
		}
		if (summary.getFiber() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getFiber()), "Food Group", "Fiber");
			maxN = summary.getFiber() > maxN ? summary.getFiber() : maxN;
			countRow++;
		}
		if (summary.getSodium() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getSodium()), "Food Group", "Sodium");
			maxN = summary.getSodium() > maxN ? summary.getSodium() : maxN;
			countRow++;
		}
		if (summary.getVitaminA() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getVitaminA()), "Food Group", "Vitamin A");
			maxN = summary.getVitaminA() > maxN ? summary.getVitaminA() : maxN;
			countRow++;
		}
		if (summary.getVitaminC() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getVitaminC()), "Food Group", "Vitamin C");
			maxN = summary.getVitaminC() > maxN ? summary.getVitaminC() : maxN;
			countRow++;
		}
		if (summary.getCalcium() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getCalcium()), "Food Group", "Calcium");
			maxN = summary.getCalcium() > maxN ? summary.getCalcium() : maxN;
			countRow++;
		}
		if (summary.getIron() > 0) {
			dataSetN.setValue(percentage(summary.getSize(), summary.getIron()), "Food Group", "Iron");
			maxN = summary.getIron() > maxN ? summary.getIron() : maxN;
			countRow++;
		}
	    try {
	    	chart = ChartFactory.createBarChart(
	                "Total Nutrition Percentage", "Nutritional Data", "Percentage (compare with total size)",
	                dataSetN, PlotOrientation.HORIZONTAL, false, true, false);
	    	maxN = maxN / 10.0;
	        generateChart(chart, dataSetN, maxN, false);
            
            
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return chart;
	}
	
	public static JFreeChart generateFoodGroupChart(GroupSummary summary) {
		JFreeChart chart = null;
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		double totalSize = summary.getConfections() + summary.getDairy() + summary.getFruit() + summary.getGrains() + summary.getMeat() + summary.getMiscellaneous() + summary.getVegetable() + summary.getWater();
		dataSet.setValue(percentage(totalSize, summary.getFruit()), "Food Group", "fruit");
        dataSet.setValue(percentage(totalSize, summary.getVegetable()), "Food Group", "vegetable");
        dataSet.setValue(percentage(totalSize, summary.getMeat()), "Food Group", "meat");
        dataSet.setValue(percentage(totalSize, summary.getGrains()), "Food Group", "grains, beans and legumes");
        dataSet.setValue(percentage(totalSize, summary.getDairy()), "Food Group", "dairy");
        dataSet.setValue(percentage(totalSize, summary.getConfections()), "Food Group", "confections");
        dataSet.setValue(percentage(totalSize, summary.getWater()), "Food Group", "water");
	    try {
	    	chart = ChartFactory.createBarChart(
	                "Total Food Group Percentage", "Food Group", "Percentage (compare with total size)",
	                dataSet, PlotOrientation.HORIZONTAL, false, true, false);
	        double max = Math.max(summary.getFruit(), Math.max(summary.getVegetable(), Math.max(summary.getMeat(), Math.max(summary.getGrains(), Math.max(summary.getDairy(), Math.max(summary.getConfections(), Math.max(summary.getMiscellaneous(), summary.getWater())))))));
	    	
	        max = max / 100.0;
	        generateChart(chart, dataSet, max, true);
            
            
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return chart;
	}
	
	private static void generateChart(JFreeChart chart, DefaultCategoryDataset dataSet, double max, boolean color) {
		try {
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
		        renderer = new Controller.CustomRenderer(
	                new Paint[] {Color.magenta, Color.blue, Color.cyan, Color.green,
	                    Color.yellow, new Color(255, 128, 0), Color.red,
	                    Color.pink}
	            );
	        } else {
	        	renderer = new Controller.CustomRenderer(
		                new Paint[] {new Color(255, 128, 0)}
		            );
	        }
	        
	        DecimalFormat decimalformat1 = new DecimalFormat("##.##'%'");
	        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1));
	        //renderer.setItemLabelsVisible(true);
	        renderer.setBaseItemLabelsVisible(true);
	        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT));
            plot.setRenderer(renderer);
            
            
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	private static List<Summary> query(String query, int titleIndex) throws ClassNotFoundException, SQLException {
		
		CellSet result = OLAPQuery(query);
		
		List<Summary> sumList = new ArrayList<Summary>();
		
		for(int i = 0; i < result.getAxes().get(1).getPositionCount(); i++) {

			Summary summary = new Summary();
			
			Member member = result.getAxes().get(1).getPositions().get(i).getMembers().get(titleIndex);
			String title = "";
			if (member != null) {
				title = member.getName();
			}
			/*DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date inputDate = null;
			try {
				inputDate = dateFormat.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}*/
            summary.setTitle(title);
			
	        for (int j = 0; j < result.getAxes().get(0).getPositionCount(); j++) {
	        	ArrayList<Integer> list = new ArrayList<Integer>();
	            list.add(j);
	            list.add(i);
	        	switch (j) {
				case 0: summary.setSize(result.getCell(list).getDoubleValue());break;
				case 1: summary.setCalories(result.getCell(list).getDoubleValue());break;
				case 2: summary.setProteins(result.getCell(list).getDoubleValue());break;
				case 3: summary.setCarbohydrates(result.getCell(list).getDoubleValue());break;
				case 4: summary.setSugar(result.getCell(list).getDoubleValue());break;
				case 5: summary.setFat(result.getCell(list).getDoubleValue());break;
				case 6: summary.setSaturatedFat(result.getCell(list).getDoubleValue());break;
				case 7: summary.setFiber(result.getCell(list).getDoubleValue());break;
				case 8: summary.setCholesterol(result.getCell(list).getDoubleValue());break;
				case 9: summary.setSodium(result.getCell(list).getDoubleValue());break;
				case 10: summary.setCalcium(result.getCell(list).getDoubleValue());break;
				case 11: summary.setIron(result.getCell(list).getDoubleValue());break;
				case 12: summary.setVitaminA(result.getCell(list).getDoubleValue());break;
				case 13: summary.setVitaminC(result.getCell(list).getDoubleValue());break;
				}
				
	        }
	        sumList.add(summary);
	    }
		
		return sumList;
		
	}
	
	private static GroupSummary queryGroup(String query, int titleIndex) throws ClassNotFoundException, SQLException {
		
		CellSet result = OLAPQuery(query);
		
		GroupSummary summary = new GroupSummary();
		for(int i = 0; i < result.getAxes().get(1).getPositionCount(); i++) {

        	ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(0);
            list.add(i);
        	switch (i) {
			case 0: summary.setConfections(result.getCell(list).getDoubleValue());break;
			case 1: summary.setDairy(result.getCell(list).getDoubleValue());break;
			case 2: summary.setFruit(result.getCell(list).getDoubleValue());break;
			case 3: summary.setGrains(result.getCell(list).getDoubleValue());break;
			case 4: summary.setMeat(result.getCell(list).getDoubleValue());break;
			case 5: summary.setMiscellaneous(result.getCell(list).getDoubleValue());break;
			case 6: summary.setVegetable(result.getCell(list).getDoubleValue());break;
			case 7: summary.setWater(result.getCell(list).getDoubleValue());break;
        	}
		}
		
		return summary;
		
	}
	
	private static CellSet OLAPQuery(String query) {
		CellSet result = null;
		try {
			// Register driver.
			Class.forName("mondrian.olap4j.MondrianOlap4jDriver");
	
			// Create connection.
			/*Connection connection = DriverManager
					.getConnection("jdbc:mondrian:JdbcDrivers=oracle.jdbc.driver.OracleDriver;"
							+ "Jdbc=jdbc:oracle:thin:WCDataWarehouse/WCDataWarehouse@192.168.56.12:1521:orcl;"
							+ "Catalog=/Users/vamhan/Downloads/apache-tomcat-7.0.52/webapps/mondrian2/WEB-INF/queries/player2.xml;");
	
			String query = "select [Measures].[COUNTtourparticipate] ON COLUMNS,"
					+ "Order([Players.Position Hierachy].[All Players.Position Hierachys].Children, [Measures].[COUNTtourparticipate], BDESC) ON ROWS "
					+ "from [Player_Stat]";*/
			
			//Class.forName("org.olap4j.driver.xmla.XmlaOlap4jDriver");
			
			//Connection connection = DriverManager
				//	.getConnection("jdbc:mondrian:Jdbc=jdbc:sqlserver://54.149.71.241:1433;jdbc.databaseName=Bond_dw;Catalog=/Users/vamhan/Desktop/NutritionSchema2.xml;JdbcDrivers=com.microsoft.sqlserver.jdbc.SQLServerDriver;JdbcUser=Admin;JdbcPassword=BIP_project");
			Connection connection = DBConnector.connectWithCatelog("Bond_dw", "/Users/vamhan/Desktop/NutritionSchema2.xml");
	
			OlapConnection olapConnection;
				olapConnection = connection.unwrap(OlapConnection.class);
			
	
			// Prepare a statement.
			result = olapConnection.createStatement().executeOlapQuery(query);
			
			RectangularCellSetFormatter formatter = new RectangularCellSetFormatter(false);
	
			// Print out.
			PrintWriter writer = new PrintWriter(System.out);
			formatter.format(result, writer);
			writer.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static double percentage(double size, double nutri){
		if (size != 0) {
			return roundTo2Decimals((nutri * 100.0) / size);
		} else {
			return 0;
		}
	}
	
	private static double roundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
	}
}