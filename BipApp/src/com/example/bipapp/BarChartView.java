package com.example.bipapp;


import org.afree.chart.ChartFactory;
import org.afree.chart.AFreeChart;
import org.afree.chart.axis.CategoryAxis;
import org.afree.chart.axis.CategoryLabelPositions;
import org.afree.chart.axis.NumberAxis;
import org.afree.chart.plot.CategoryPlot;
import org.afree.chart.plot.PlotOrientation;
import org.afree.data.category.CategoryDataset;
import org.afree.data.category.DefaultCategoryDataset;
import org.afree.graphics.SolidColor;
import org.afree.graphics.geom.Font;
import org.afree.chart.labels.ItemLabelAnchor;
import org.afree.chart.labels.ItemLabelPosition;
import org.afree.chart.labels.StandardCategoryItemLabelGenerator;
import org.afree.chart.renderer.category.CategoryItemRenderer;
import org.afree.ui.TextAnchor;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Paint;

import java.util.List;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;

/**
 * PieChartDemo1View
 */
public class BarChartView extends DemoView {

    /**
     * constructor
     * @param context
     */
    public BarChartView(Context context) {
        super(context);
    }

    public void drawChart(CategoryDataset dataset, String title, String domain, double max, boolean color) {
        AFreeChart chart = createChart(dataset, title, domain, max, color);

        setChart(chart);
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private static AFreeChart createChart(CategoryDataset dataset, String chartTitle, String domain, double max, boolean color) {

        // create the chart...
        AFreeChart chart = ChartFactory.createBarChart(
                chartTitle,       // chart title
                domain,               // domain axis label
                "Percentage",                  // range axis label
                dataset,                  // data
                PlotOrientation.HORIZONTAL, // orientation
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
        //rangeAxis.setRange(0.00, max + (max / 7.0));


        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                        Math.PI / 6.0));
        domainAxis.setTickLabelFont(font2);

        CategoryItemRenderer renderer;
        if (color) {
            renderer = new CustomRenderer(
                    new Paint[] {new Paint(Color.MAGENTA), new Paint(Color.BLUE), new Paint(Color.CYAN), new Paint(Color.GREEN),
                            new Paint(Color.YELLOW), new Paint(Color.argb(1, 255, 128, 0)), new Paint(Color.RED), new Paint(Color.argb(1, 255, 105, 180))}
            );
        } else {
            renderer = new CustomRenderer(
                    new Paint[] {new Paint(Color.argb(1, 255, 128, 0))}
            );
        }

        /*DecimalFormat decimalformat1 = new DecimalFormat("##.##'%'");
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1));
        //renderer.setItemLabelsVisible(true);
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT));
        plot.setRenderer(renderer);*/

        return chart;

    }
}