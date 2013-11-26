package com.tuwien.snowwhite;

import android.util.*;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.content.Context;
import android.graphics.*;
import android.widget.*;

public class SampleView extends ImageView {
	final static int N_POINTS = 77;
	public PointF[] m_vPoint = new PointF[N_POINTS];
	
    private Bitmap bm;
    private Paint  rPaint;
    private Paint  bPaint;
    private int ww = 320;
    private int hh = 480;
    private boolean drawMark = false;
    private Context c;
    
	public SampleView(Context context, AttributeSet attrs) {
		super(context, attrs);
        setFocusable(true);
        
        c = context;
        
        rPaint = new Paint();
        rPaint.setColor(Color.RED);
        rPaint.setStyle(Paint.Style.STROKE);
        
        bPaint = new Paint();
        bPaint.setColor(Color.BLUE);
        bPaint.setStyle(Paint.Style.STROKE);  
        
		for(int i = 0; i < N_POINTS; i++) {
			m_vPoint[i] = new PointF(0.0f, 0.0f);
		}
	}
	
    public void setBM(Bitmap bmImg) {
    	bm = bmImg;
    	drawMark = false;
    }
        
    public void setBM(Bitmap bmImg, int[] points, float rw, float rh) {
    	bm = bmImg;
    	ww = bm.getWidth();
    	hh = bm.getHeight();
    	for (int i = 0; i < points.length/2; i++) {
    		m_vPoint[i].x = points[2*i]*rw;
    		m_vPoint[i].y = points[2*i+1]*rh;
    	}
    	drawMark = true;
    }
    
    public void setBM(Bitmap bmImg, int[] points) {
    	bm = bmImg;
    	ww = bm.getWidth();
    	hh = bm.getHeight();
    	for (int i = 0; i < points.length/2; i++) {
    		m_vPoint[i].x = points[2*i];
    		m_vPoint[i].y = points[2*i+1];
    	}
    	drawMark = true;
    }       
    
    @Override 
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
    	if (bm != null && !bm.isRecycled()) {
    		canvas.drawBitmap(bm, 0, 0, null);
    	}
    	if (bm == null)
    		Toast.makeText(c, "NULL", Toast.LENGTH_LONG).show();
    	else if (bm.isRecycled())
    		Toast.makeText(c, "REC", Toast.LENGTH_LONG).show();
    		
        if (drawMark) DrawPoints(canvas, 3);
    }
    
    void DrawPoints(Canvas canvas, int s) {
    	int x, y, x1, x2, y1, y2;
    	int w = ww - 1;
    	int h = hh - 1;
    	for(int i = 0; i < m_vPoint.length; i++) {
    		//draw a red + on each point
    		x = (int)m_vPoint[i].x;
    		y = (int)m_vPoint[i].y;
    		x1 = x-s;
    		x2 = x+s;
    		y1 = y-s;
    		y2 = y+s;
    		if (x < 0) { x1 = 0; x2 = 1; } 
    		if (y < 0) { y1 = 0; y2 = 1; }
    		if (x > w) { x1 = w-1; x2 = w; } 
    		if (y > h) { y1 = h-1; y2 = h; } 
    		canvas.drawLine(x1, y, x2, y, rPaint);
    		canvas.drawLine(x, y1, x, y2, rPaint);
    	}
    }

    void DrawLine(Canvas canvas, int p1, int p2) {
    	int x1, y1, x2, y2;
    	int w = ww - 1;
    	int h = hh - 1;
    	x1 = (int)m_vPoint[p1].x;
    	y1 = (int)m_vPoint[p1].y;
    	x2 = (int)m_vPoint[p2].x;
    	y2 = (int)m_vPoint[p2].y;
    	if (x1 < 0) x1 = 0; if (x2 < 0) x2 = 0;
    	if (y1 < 0) y1 = 0; if (y2 < 0) y2 = 0;
    	if (x1 > w) x1 = w; if (x2 > w) x2 = w;
    	if (y1 > h) y1 = h; if (y2 > h) y2 = h;
    	canvas.drawLine(x1, y1, x2, y2, bPaint);
    }     
}