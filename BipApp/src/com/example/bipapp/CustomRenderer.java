package com.example.bipapp;

import android.graphics.Paint;

import org.afree.chart.renderer.category.BarRenderer;

public class CustomRenderer extends BarRenderer {

    private Paint[] colors;

    public CustomRenderer(final Paint[] colors) {
        this.colors = colors;
    }

    public Paint getItemPaint(final int row, final int column) {
        return this.colors[column % this.colors.length];
    }
}
