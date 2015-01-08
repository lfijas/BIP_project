package com.example.bipapp;


import java.text.DateFormatSymbols;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.axis.CategoryAxis;
import org.afree.chart.axis.CategoryLabelPositions;
import org.afree.chart.axis.NumberAxis;
import org.afree.chart.plot.CategoryPlot;
import org.afree.chart.plot.PlotOrientation;
import org.afree.data.category.CategoryDataset;
import org.afree.data.category.DefaultCategoryDataset;
import org.afree.graphics.SolidColor;
import org.afree.graphics.geom.Font;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;

/**
 * PieChartDemo1View
 */
public class LineChartView extends DemoView {

    /**
     * constructor
     * @param context
     */
    public LineChartView(Context context) {
        super(context);
    }
    
    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public LineChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void drawChart(JSONArray result, String measure, String year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            for (int i = 0; i < result.length(); i++) {
                dataset.addValue(((JSONObject) result.get(i)).getDouble(measure), "Measure", new DateFormatSymbols().getShortMonths()[((JSONObject) result.get(i)).getInt("title") - 1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AFreeChart chart = createChart(dataset, "Total " + measure + " in " + year);

        setChart(chart);
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private static AFreeChart createChart(CategoryDataset dataset, String chartTitle) {

        // create the chart...
        AFreeChart chart = ChartFactory.createLineChart(
                chartTitle,       // chart title
                "Month",               // domain axis label
                "Measure",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                false,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaintType(new SolidColor(Color.WHITE));

        // get a reference to the plot for further customisation...

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        chart.getTitle().setFont(new Font("SansSerif", Typeface.BOLD, 25));
        Font font = new Font("SansSerif", Typeface.NORMAL, 20);
        plot.getDomainAxis().setLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);

        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        Font font2 = new Font("SansSerif", Typeface.NORMAL, 15);
        rangeAxis.setTickLabelFont(font2);


        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                        Math.PI / 6.0));
        domainAxis.setTickLabelFont(font2);
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }
}