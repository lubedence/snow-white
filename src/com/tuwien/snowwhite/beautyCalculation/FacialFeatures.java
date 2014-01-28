/*******************************************************************************
 * Copyright (c) 2014 Lukas Furlan (furlan.lukas@gmail.com)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Lukas Furlan - initial API and implementation
 ******************************************************************************/
package com.tuwien.snowwhite.beautyCalculation;

import java.util.zip.DataFormatException;

import com.tuwien.snowwhite.R;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class FacialFeatures {
	
	private class FeaturePoints{
		public int x;
		public int y;
		
		public FeaturePoints(int x, int y){
			this.x = x;
			this.y = y;
		}
		
	}
	
	private String[] ratioText = null;
	private String[] symmetryText = null;
	
	private final int FEATURECOUNT = 77;
	private FeaturePoints[] points = new FeaturePoints[FEATURECOUNT];
	
	public static float GOLDENRATIO = 1.618f;
	private static float MAX = 0.98f;
	private static float MIN = 0.3f;
	
	private Context context = null;
	
	public FacialFeatures(int[] p, Context c) throws DataFormatException{
		if(p.length != FEATURECOUNT*2) 
			throw new DataFormatException("Wrong array length. Should be "+FEATURECOUNT+"*2");
		
		context = c;
		
		for(int i=0; i<FEATURECOUNT; i++) 
			points[i] = new FeaturePoints(p[2*i], p[2*i+1]);	
	}
	
	
	public String[] featureRatiosText(){
		return ratioText;
	}
	
	public String[] symmetryText(){
		return symmetryText;
	}
	
	
	public float[] featureRatios(){
		
		float[] deviation = new float[7];
		ratioText = new String[7];
		//VERTICAL------------------------------------------------------------------

		//eye center - nose bottom - mouth bottom
		int eye_nose = points[PN.NOSE_BOTTOM].y - eyeHeight();
		int nose_mouth = points[PN.MOUTH_BOTTOM].y - points[PN.NOSE_BOTTOM].y;
		deviation[0] = goldenRatio(eye_nose, nose_mouth);
		ratioText[0] = context.getString(R.string.eye_nose_mouth); 
		
		//eye center - mouth center - chin bottom
		int eye_mouth = mouthCenterHeight() - eyeHeight();
		int mouth_center_chin = points[PN.FACE_BOTTOM].y - mouthCenterHeight();
		deviation[1] = goldenRatio(eye_mouth, mouth_center_chin);
		ratioText[1] = context.getString(R.string.eye_mouth_chin);
		
		//face top - nose peak - chin bottom
		int face_top_nose = points[PN.NOSE_PEAK].y - points[PN.FACE_TOP].y; 
		int nose_chin = points[PN.FACE_BOTTOM].y - points[PN.NOSE_PEAK].y; 
		deviation[2] = goldenRatio(face_top_nose, nose_chin);
		ratioText[2] = context.getString(R.string.forehead_nose_chin);
		
		//HORIZONTAL---------------------------------------------------------------
		
		//nose,eye,face-width in relation
		int nose_width = noseWidth();//should be 1.00
		int eyes_outer_dist = eyesOuterDistance();//should be phi^2
		int face_width = faceWidth();//should be phi^3
		
		float a = goldenRatio2(nose_width, eyes_outer_dist,2);
		float b = goldenRatio2(nose_width, face_width,3);
		
		deviation[3] = (a+b)/2;
		ratioText[3] = context.getString(R.string.nose_eye_faceWidth);
		
		//mouth width - space between eyes
		deviation[4] = goldenRatio(mouthWidth(), eyesInnerDistance());
		ratioText[4] = context.getString(R.string.mouth_eyeDistance);
		
		//mouth width - nose width
		deviation[5] = goldenRatio(mouthWidth(), noseWidth());
		ratioText[5] = context.getString(R.string.mouth_nose);
				
		//HORIZONTAL AND VERTICAL--------------------------------------------------
		
		//face height - face width
		deviation[6] = goldenRatio(faceHeight(), faceWidth());
		ratioText[6] = context.getString(R.string.faceHeight_faceWidth);
		
		return deviation;
		
	}
	
	//distance A to C
	//B should be the golden ratio of the distance A to C
	//return: deviation in percentage
	private float goldenRatio(int a, int b, int c){
		return goldenRatio(Math.abs(a-b), Math.abs(c-b));
	}
	
	//distAB+distBC= distance A to C
	//B should be the golden ratio of the distance A to C
	//return: deviation in percentage
	private float goldenRatio(float distAB, float distBC){
		float ratio = 0;
		
		if(distAB>distBC)	ratio = distAB/distBC;
		else				ratio = distBC/distAB;
		
		float diff = Math.abs(ratio-GOLDENRATIO);
		Log.d("CHECK", diff+"");
		Log.d("CHECK", ((float)Math.exp(-diff))+"");
		diff = (float) ((Math.exp(-diff) - MIN)/(MAX-MIN));
		
		if(Float.compare(diff, 1.0f) >=0)		diff = 1.0f;
		else if(Float.compare(diff, 0.0f) <=0)	diff = 0.0f;
		
		float percent = (1.0f - diff)*100;
		
		Log.d("CHECK", percent+"");
		

		return percent;
	}
	
	//distAB+distBC= distance A to C
	//B should be the (golden ratio)^pow of the distance A to C
	//return: deviation in percentage
	private float goldenRatio2(float distAB, float distBC,int pow){
		float ratio = 0;
		
		if(distAB>distBC)	ratio = distAB/distBC;
		else				ratio = distBC/distAB;
		
		float diff = (float) Math.abs(ratio-Math.pow(GOLDENRATIO,pow));
		diff = (float) ((Math.exp(-diff) - 0.01)/(MAX-0.01));
		
		if(Float.compare(diff, 1.0f) >=0)		diff = 1.0f;
		else if(Float.compare(diff, 0.0f) <=0)	diff = 0.0f;
		
		float percent = (1.0f - diff)*100;
		
		Log.d("CHECK_2", percent+"");
		

		return percent;
	}
	
		
	//symmetry of mouth, nose and eyes
	//symmetry line goes from FACE_TOP to FACE_BOTTOM
	public float[] checkSymmetry(){
		//example
		//symmetry line -> X
		//line from mouth left to right -> Y
		//calculate intersection point of X and Y
		//both part lines(B1 and B2) should have the same length
		//TODO ideally, there should be a right angle between X and Y
		
		float[] deviation = new float[4];
		symmetryText = new String[4];
		
		//vertical symmetry-line. C1 = A1*x+B1*y
		int A1 = points[PN.FACE_BOTTOM].y - points[PN.FACE_TOP].y;
		int B1 = points[PN.FACE_TOP].x - points[PN.FACE_BOTTOM].x;
		int C1 = A1*points[PN.FACE_TOP].x+B1*points[PN.FACE_TOP].y;
		
		//start and end point of line B
		float[] start = new float[2];
		float[] end = new float[2];
		
		start[0] = points[PN.MOUTH_LEFT].x;
		start[1] = points[PN.MOUTH_LEFT].y;
		end[0] = points[PN.MOUTH_RIGHT].x;
		end[1] = points[PN.MOUTH_RIGHT].y;	
		deviation[0] = symRatio(start,end,intersectionPoint(A1, B1, C1, start, end));
		symmetryText[0] = context.getString(R.string.mouth);
		
		start[0] = points[PN.NOSE_LEFT].x;
		start[1] = points[PN.NOSE_LEFT].y;
		end[0] = points[PN.NOSE_RIGHT].x;
		end[1] = points[PN.NOSE_RIGHT].y;	
		deviation[1] = symRatio(start,end,intersectionPoint(A1, B1, C1, start, end));
		symmetryText[1] = context.getString(R.string.nose);
		
		start[0] = points[PN.EYE_LEFT_OUTSIDE].x;
		start[1] = points[PN.EYE_LEFT_OUTSIDE].y;
		end[0] = points[PN.EYE_RIGHT_OUTSIDE].x;
		end[1] = points[PN.EYE_RIGHT_OUTSIDE].y;	
		deviation[2] = symRatio(start,end,intersectionPoint(A1, B1, C1, start, end));
		symmetryText[2] = context.getString(R.string.eyesInside);
		
		start[0] = points[PN.EYE_LEFT_INSIDE].x;
		start[1] = points[PN.EYE_LEFT_INSIDE].y;
		end[0] = points[PN.EYE_RIGHT_INSIDE].x;
		end[1] = points[PN.EYE_RIGHT_INSIDE].y;	
		deviation[3] = symRatio(start,end,intersectionPoint(A1, B1, C1, start, end));
		symmetryText[3] = context.getString(R.string.eyesOutside);
		
		return deviation;
	}
	//array-length of start, end and half == [2] with x on [0] and y on [1]
	private float symRatio(float[] start, float[] end, float[] half){
		if(half == null)	return 100; //just in case, if no intersection point can be found (parallel lines). should never happen
		float deltaX = Math.abs(end[0] - start[0]);
		float deltaY = Math.abs(end[1] - start[1]);
		float length = (float)Math.sqrt((double)(deltaX*deltaX+deltaY*deltaY));
		deltaX = Math.abs(half[0] - start[0]);
		deltaY = Math.abs(half[1] - start[1]);
		float lengthHalf = (float)Math.sqrt((double)(deltaX*deltaX+deltaY*deltaY));
		
		float diff = Math.abs((lengthHalf - (length/2)) / (length/2));
		Log.d("SYM", diff+"");
		diff = (float) ((Math.exp(-diff) - 0.8)/(0.9999-0.8));	
		if(Float.compare(diff, 1.0f) >=0)		diff = 1.0f;
		else if(Float.compare(diff, 0.0f) <=0)	diff = 0.0f;
		float percent = (1.0f - diff)*100;
		
		return percent;
	}
	
	//array-length of start and end == [2] with x on [0] and y on [1]
	//A1,B1,C1 coeff. of the symmetry line
	private float[] intersectionPoint(int A1, int B1, int C1, float[] start, float[] end){
		float A2 = end[1]-start[1];
		float B2 = start[0]-end[0];
		float C2 = A2*start[0]+B2*start[1];
		
		float det = A1*B2 - A2*B1;
		if(det == 0.0f) return null; //parallel lines
		
		float[] intP = new float[2];
		
		intP[0] = (B2*C1 - B1*C2)/det;
		intP[1] = (A1*C2 - A2*C1)/det;
		
		return intP;
	}
	
	private int faceWidth(){
		return Math.abs(points[PN.FACE_RIGHT].x - points[PN.FACE_LEFT].x);
	}
	
	private int faceHeight(){
		return Math.abs(points[PN.FACE_TOP].y - points[PN.FACE_BOTTOM].y);
	}
	
	private int eyesOuterDistance(){
		return Math.abs(points[PN.EYE_RIGHT_OUTSIDE].x - points[PN.EYE_LEFT_OUTSIDE].x);
	}
	
	private int eyesInnerDistance(){
		return Math.abs(points[PN.EYE_RIGHT_INSIDE].x - points[PN.EYE_LEFT_INSIDE].x);
	}
	
	private int eyeLeftWidth(){
		return Math.abs(points[PN.EYE_LEFT_OUTSIDE].x - points[PN.EYE_LEFT_INSIDE].x);
	}
	
	private int eyeRightWidth(){
		return Math.abs(points[PN.EYE_RIGHT_OUTSIDE].x - points[PN.EYE_RIGHT_INSIDE].x);
	}
	
	private int noseWidth(){
		return Math.abs(points[PN.NOSE_RIGHT].x - points[PN.NOSE_LEFT].x);
	}
	
	private int mouthWidth(){
		return Math.abs(points[PN.MOUTH_RIGHT].x - points[PN.MOUTH_LEFT].x);
	}
	
	private int mouthCenterHeight(){
		return (points[PN.MOUTH_BOTTOMLIP].y+points[PN.MOUTH_UPPERLIP].y) / 2;
	}
	
	private int mouthTopHeight(){
		return (points[PN.MOUTH_TOP_LEFT].y+points[PN.MOUTH_TOP].y+points[PN.MOUTH_TOP_RIGHT].y) / 3;
	}
	
	private int eyeHeight(){
		return (points[PN.EYE_LEFT_CENTER].y+points[PN.EYE_RIGHT_CENTER].y) / 2;
	}

}
