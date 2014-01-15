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
    private boolean drawMark = false;
    
    private int featureVisType = 0;
    
	public SampleView(Context context, AttributeSet attrs) {
		super(context, attrs);
        setFocusable(true);
        
        rPaint = new Paint();
        rPaint.setColor(Color.RED);
        rPaint.setStyle(Paint.Style.STROKE);
        
        //visualization type: dots(0) or mask(1)
        featureVisType = MyStoredData.getInstance().getSharedPreferences().getInt(context.getString(R.string.settings_featureVis_id), 0);

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
    	for (int i = 0; i < points.length/2; i++) {
    		m_vPoint[i].x = points[2*i]*rw;
    		m_vPoint[i].y = points[2*i+1]*rh;
    	}
    	drawMark = true;
    }
    
    public void setBM(Bitmap bmImg, int[] points) {
    	bm = bmImg;
    	for (int i = 0; i < points.length/2; i++) {
    		m_vPoint[i].x = points[2*i];
    		m_vPoint[i].y = points[2*i+1];
    	}
    	drawMark = true;
    }       
    
    @Override 
    protected void onDraw(Canvas canvas) {
    	if (bm != null && !bm.isRecycled()) {
    		canvas.drawBitmap(bm, 0, 0, null);
    	}
    		
        if (drawMark)
        {
        	if(featureVisType == 1)
        		DrawMask(canvas);
        	else
        		DrawPoints(canvas);
        }
    }
    
    void DrawPoints(Canvas canvas) {
    	for(int i = 0; i < m_vPoint.length; i++) {
    		canvas.drawCircle((int)m_vPoint[i].x, (int)m_vPoint[i].y, (2 * this.getResources().getDisplayMetrics().density), rPaint);
    	}
    }
    
    void DrawMask(Canvas canvas) {
    	for(int i = 1; i < 16; i++) {
    		canvas.drawLine((int)m_vPoint[i-1].x, (int)m_vPoint[i-1].y, (int)m_vPoint[i].x, (int)m_vPoint[i].y, rPaint);
    	}
    	canvas.drawLine((int)m_vPoint[15].x, (int)m_vPoint[15].y, (int)m_vPoint[0].x, (int)m_vPoint[0].y, rPaint);
    	
    	myDrawLine(canvas, m_vPoint[0], m_vPoint[50]);
    	myDrawLine(canvas, m_vPoint[1], m_vPoint[50]);
    	myDrawLine(canvas, m_vPoint[1], m_vPoint[58]);
    	myDrawLine(canvas, m_vPoint[2], m_vPoint[58]);
    	myDrawLine(canvas, m_vPoint[3], m_vPoint[58]);
    	myDrawLine(canvas, m_vPoint[3], m_vPoint[59]);
    	myDrawLine(canvas, m_vPoint[4], m_vPoint[59]);
    	myDrawLine(canvas, m_vPoint[5], m_vPoint[59]);
    	myDrawLine(canvas, m_vPoint[5], m_vPoint[76]);
    	myDrawLine(canvas, m_vPoint[5], m_vPoint[75]);
    	myDrawLine(canvas, m_vPoint[6], m_vPoint[75]);
    	myDrawLine(canvas, m_vPoint[6], m_vPoint[74]);
    	
    	myDrawLine(canvas, m_vPoint[50], m_vPoint[49]);
    	myDrawLine(canvas, m_vPoint[50], m_vPoint[52]);
    	myDrawLine(canvas, m_vPoint[50], m_vPoint[58]);
    	myDrawLine(canvas, m_vPoint[58], m_vPoint[51]);
    	myDrawLine(canvas, m_vPoint[58], m_vPoint[52]);
    	myDrawLine(canvas, m_vPoint[58], m_vPoint[57]);
    	myDrawLine(canvas, m_vPoint[58], m_vPoint[60]);
    	myDrawLine(canvas, m_vPoint[57], m_vPoint[51]);
    	myDrawLine(canvas, m_vPoint[57], m_vPoint[56]);
    	myDrawLine(canvas, m_vPoint[57], m_vPoint[60]);
    	myDrawLine(canvas, m_vPoint[57], m_vPoint[61]);
    	myDrawLine(canvas, m_vPoint[56], m_vPoint[52]);
    	myDrawLine(canvas, m_vPoint[56], m_vPoint[61]);
    	myDrawLine(canvas, m_vPoint[56], m_vPoint[62]);
    	myDrawLine(canvas, m_vPoint[52], m_vPoint[49]);
    	
    	myDrawLine(canvas, m_vPoint[59], m_vPoint[60]);
    	myDrawLine(canvas, m_vPoint[59], m_vPoint[76]);
    	myDrawLine(canvas, m_vPoint[59], m_vPoint[69]);
    	myDrawLine(canvas, m_vPoint[59], m_vPoint[68]);
    	myDrawLine(canvas, m_vPoint[60], m_vPoint[61]);
    	myDrawLine(canvas, m_vPoint[60], m_vPoint[68]);
    	myDrawLine(canvas, m_vPoint[61], m_vPoint[62]);
    	myDrawLine(canvas, m_vPoint[61], m_vPoint[67]);
    	myDrawLine(canvas, m_vPoint[62], m_vPoint[67]);
    	myDrawLine(canvas, m_vPoint[76], m_vPoint[69]);
    	myDrawLine(canvas, m_vPoint[76], m_vPoint[75]);
    	myDrawLine(canvas, m_vPoint[75], m_vPoint[74]);
    	myDrawLine(canvas, m_vPoint[75], m_vPoint[69]);
    	myDrawLine(canvas, m_vPoint[69], m_vPoint[70]);
    	myDrawLine(canvas, m_vPoint[69], m_vPoint[74]);
    	myDrawLine(canvas, m_vPoint[74], m_vPoint[70]);
    	myDrawLine(canvas, m_vPoint[59], m_vPoint[58]);
    	myDrawLine(canvas, m_vPoint[68], m_vPoint[67]);
    	myDrawLine(canvas, m_vPoint[68], m_vPoint[61]);
    	myDrawLine(canvas, m_vPoint[51], m_vPoint[52]);
    	myDrawLine(canvas, m_vPoint[51], m_vPoint[56]);
    	
    	/*for(int i = 30; i < 37; i++) {
    		myDrawLine(canvas, m_vPoint[i], m_vPoint[i++]);
    		myDrawLine(canvas, m_vPoint[i], m_vPoint[38]);
    	}*/
    	
    	//left eye
    	myDrawLine(canvas, m_vPoint[30], m_vPoint[31]);
    	myDrawLine(canvas, m_vPoint[31], m_vPoint[32]);
    	myDrawLine(canvas, m_vPoint[32], m_vPoint[33]);
    	myDrawLine(canvas, m_vPoint[33], m_vPoint[34]);
    	myDrawLine(canvas, m_vPoint[34], m_vPoint[35]);
    	myDrawLine(canvas, m_vPoint[35], m_vPoint[36]);
    	myDrawLine(canvas, m_vPoint[36], m_vPoint[37]);
    	myDrawLine(canvas, m_vPoint[30], m_vPoint[38]);
    	myDrawLine(canvas, m_vPoint[31], m_vPoint[38]);
    	myDrawLine(canvas, m_vPoint[32], m_vPoint[38]);
    	myDrawLine(canvas, m_vPoint[33], m_vPoint[38]);
    	myDrawLine(canvas, m_vPoint[34], m_vPoint[38]);
    	myDrawLine(canvas, m_vPoint[35], m_vPoint[38]);
    	myDrawLine(canvas, m_vPoint[36], m_vPoint[38]);

    	myDrawLine(canvas, m_vPoint[37], m_vPoint[30]);
    	myDrawLine(canvas, m_vPoint[37], m_vPoint[38]);
    	
    	
    	//forehead
    	myDrawLine(canvas, m_vPoint[30], m_vPoint[49]);
    	myDrawLine(canvas, m_vPoint[30], m_vPoint[50]);
    	myDrawLine(canvas, m_vPoint[0], m_vPoint[34]);
    	myDrawLine(canvas, m_vPoint[15], m_vPoint[49]);
    	myDrawLine(canvas, m_vPoint[15], m_vPoint[30]);
    	myDrawLine(canvas, m_vPoint[15], m_vPoint[32]);
    	myDrawLine(canvas, m_vPoint[14], m_vPoint[49]);
    	
    	
    	//------------right side of face--------------
    	
    	//right eye
    	myDrawLine(canvas, m_vPoint[40], m_vPoint[41]);
    	myDrawLine(canvas, m_vPoint[41], m_vPoint[42]);
    	myDrawLine(canvas, m_vPoint[42], m_vPoint[43]);
    	myDrawLine(canvas, m_vPoint[43], m_vPoint[44]);
    	myDrawLine(canvas, m_vPoint[44], m_vPoint[45]);
    	myDrawLine(canvas, m_vPoint[45], m_vPoint[46]);
    	myDrawLine(canvas, m_vPoint[46], m_vPoint[47]);
    	myDrawLine(canvas, m_vPoint[40], m_vPoint[39]);
    	myDrawLine(canvas, m_vPoint[41], m_vPoint[39]);
    	myDrawLine(canvas, m_vPoint[42], m_vPoint[39]);
    	myDrawLine(canvas, m_vPoint[43], m_vPoint[39]);
    	myDrawLine(canvas, m_vPoint[44], m_vPoint[39]);
    	myDrawLine(canvas, m_vPoint[45], m_vPoint[39]);
    	myDrawLine(canvas, m_vPoint[46], m_vPoint[39]);

    	myDrawLine(canvas, m_vPoint[47], m_vPoint[40]);
    	myDrawLine(canvas, m_vPoint[47], m_vPoint[39]);
    	
    	
    	//forehead
    	myDrawLine(canvas, m_vPoint[13], m_vPoint[42]);
    	myDrawLine(canvas, m_vPoint[13], m_vPoint[40]);
    	myDrawLine(canvas, m_vPoint[13], m_vPoint[49]);
    	myDrawLine(canvas, m_vPoint[40], m_vPoint[49]);
    	myDrawLine(canvas, m_vPoint[40], m_vPoint[48]);
    	myDrawLine(canvas, m_vPoint[12], m_vPoint[44]);
    	
    	
    	myDrawLine(canvas, m_vPoint[11], m_vPoint[48]);
    	myDrawLine(canvas, m_vPoint[11], m_vPoint[54]);
    	myDrawLine(canvas, m_vPoint[10], m_vPoint[54]);
    	myDrawLine(canvas, m_vPoint[9], m_vPoint[54]);
    	myDrawLine(canvas, m_vPoint[48], m_vPoint[49]);
    	myDrawLine(canvas, m_vPoint[48], m_vPoint[52]);
    	myDrawLine(canvas, m_vPoint[48], m_vPoint[54]);
    	myDrawLine(canvas, m_vPoint[52], m_vPoint[54]);
    	myDrawLine(canvas, m_vPoint[52], m_vPoint[53]);
    	myDrawLine(canvas, m_vPoint[54], m_vPoint[53]);
    	myDrawLine(canvas, m_vPoint[54], m_vPoint[55]);
    	myDrawLine(canvas, m_vPoint[54], m_vPoint[65]);
    	myDrawLine(canvas, m_vPoint[54], m_vPoint[64]);
    	myDrawLine(canvas, m_vPoint[55], m_vPoint[64]);
    	myDrawLine(canvas, m_vPoint[55], m_vPoint[63]);
    	myDrawLine(canvas, m_vPoint[55], m_vPoint[56]);
    	myDrawLine(canvas, m_vPoint[53], m_vPoint[56]);
    	myDrawLine(canvas, m_vPoint[53], m_vPoint[57]);
    	myDrawLine(canvas, m_vPoint[12], m_vPoint[48]);
    	
    	//lips
    	myDrawLine(canvas, m_vPoint[74], m_vPoint[73]);
    	myDrawLine(canvas, m_vPoint[74], m_vPoint[71]);
    	myDrawLine(canvas, m_vPoint[71], m_vPoint[70]);
    	myDrawLine(canvas, m_vPoint[71], m_vPoint[72]);
    	myDrawLine(canvas, m_vPoint[71], m_vPoint[73]);
    	myDrawLine(canvas, m_vPoint[71], m_vPoint[65]);
    	myDrawLine(canvas, m_vPoint[73], m_vPoint[72]);
    	myDrawLine(canvas, m_vPoint[72], m_vPoint[65]);
    	
    	myDrawLine(canvas, m_vPoint[65], m_vPoint[64]);
    	myDrawLine(canvas, m_vPoint[65], m_vPoint[66]);
    	myDrawLine(canvas, m_vPoint[64], m_vPoint[66]);
    	myDrawLine(canvas, m_vPoint[64], m_vPoint[63]);
    	myDrawLine(canvas, m_vPoint[66], m_vPoint[67]);
    	myDrawLine(canvas, m_vPoint[66], m_vPoint[63]);
    	myDrawLine(canvas, m_vPoint[63], m_vPoint[62]);
    	myDrawLine(canvas, m_vPoint[63], m_vPoint[67]);
    	
    	myDrawLine(canvas, m_vPoint[6], m_vPoint[73]);
    	myDrawLine(canvas, m_vPoint[7], m_vPoint[72]);
    	myDrawLine(canvas, m_vPoint[7], m_vPoint[72]);
    	myDrawLine(canvas, m_vPoint[7], m_vPoint[65]);
    	myDrawLine(canvas, m_vPoint[8], m_vPoint[65]);
    	myDrawLine(canvas, m_vPoint[9], m_vPoint[65]);

    }
    
    private void myDrawLine(Canvas canvas, PointF start, PointF end){
    	canvas.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y, rPaint);
    }


    public void clearBitmap(){
    	if (bm != null && !bm.isRecycled()) {
    		bm.recycle();
    		bm = null;
    	}
    	else if (bm != null && bm.isRecycled()) {
    		bm = null;
    	}
    }
}