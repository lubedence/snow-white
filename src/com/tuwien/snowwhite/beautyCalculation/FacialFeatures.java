package com.tuwien.snowwhite.beautyCalculation;

import java.util.zip.DataFormatException;

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
	
	private final int FEATURECOUNT = 77;
	private FeaturePoints[] points = new FeaturePoints[FEATURECOUNT];
	public static float GOLDENRATIO = 1.618f;
	
	public FacialFeatures(int[] p) throws DataFormatException{
		if(p.length != FEATURECOUNT*2) 
			throw new DataFormatException("Wrong array length. Should be "+FEATURECOUNT+"*2");
		
		for(int i=0; i<FEATURECOUNT; i++) 
			points[i] = new FeaturePoints(p[2*i], p[2*i+1]);	
	}
	
	public float[] featureRatios(){
		
		float[] deviation = new float[8];
		
		//VERTICAL------------------------------------------------------------------
		
		//eye center - nose bottom - mouth bottom
		int eye_nose = points[PN.NOSE_BOTTOM].y - eyeHeight();
		int nose_mouth = points[PN.MOUTH_BOTTOM].y - points[PN.NOSE_BOTTOM].y;
		deviation[0] = goldenRatio(eye_nose, nose_mouth);
		
		//eye center - mouth center - chin bottom
		int eye_mouth = mouthCenterHeight() - eyeHeight();
		int mouth_center_chin = points[PN.FACE_BOTTOM].y - mouthCenterHeight();
		deviation[1] = goldenRatio(eye_mouth, mouth_center_chin);

		
		//face top - nose peak - chin bottom
		int face_top_nose = points[PN.NOSE_PEAK].y - points[PN.FACE_TOP].y; 
		int nose_chin = points[PN.FACE_BOTTOM].y - points[PN.NOSE_PEAK].y; 
		deviation[2] = goldenRatio(face_top_nose, nose_chin);
		
		
		//HORIZONTAL---------------------------------------------------------------
		
		//nose/eye/face-width in relation
		int nose_width = noseWidth();//1.00
		int eyes_outer_dist = eyesOuterDistance();//phi^2
		int face_width = faceWidth();//phi^3
		float a = oneToOneRatio(nose_width*GOLDENRATIO*GOLDENRATIO, eyes_outer_dist);
		float b = oneToOneRatio(nose_width*GOLDENRATIO*GOLDENRATIO*GOLDENRATIO, face_width);
		float c = oneToOneRatio(eyes_outer_dist*GOLDENRATIO, face_width);
		deviation[3] = (a+b+c)/3;
		
		//mouth width - space between eyes
		deviation[4] = goldenRatio(mouthWidth(), eyesInnerDistance());
		
		//mouth width - nose width
		deviation[5] = goldenRatio(mouthWidth(), noseWidth());
		
		//eyes width should be equal
		deviation[6] = oneToOneRatio(eyeLeftWidth(), eyeRightWidth());
		
		//BOTH---------------------------------------------------------------------
		
		//face height - face width
		deviation[7] = goldenRatio(faceHeight(), faceWidth());
		
		return deviation;
		
	}
	
	//distance a-c
	//b should be the golden ratio of the distance a-c
	//return: deviation in percentage
	private float goldenRatio(int a, int b, int c){
		return goldenRatio(Math.abs(a-b), Math.abs(c-b));
	}
	
	//distAB+distBC == distance from A to C
	private float goldenRatio(float distAB, float distBC){
		float ratio = 0;
		
		if(distAB>distBC)	ratio = distAB/distBC;
		else				ratio = distBC/distAB;
		
		float diff = Math.abs(ratio-GOLDENRATIO);
		float percent = diff*100/GOLDENRATIO;
		
		if(percent>100)	return 100;
		else			return percent;
	}
	
	private float oneToOneRatio(float distA, float distB){
		float percent = 0;
		if(distA<distB)		percent = Math.abs((distA/distB)-1)*100;
		else				percent = Math.abs((distB/distA)-1)*100;
		
		if(percent>100) return 100;
		else return percent;
	}
	
	public float[] checkSymmetry(){
		//line from face top (or nose center? cause, face top X could be not that accurate) to face bottom -> A
		//for ex.: line from mouth left to right -> B
		//calc intersection point of A and B
		//both part lines(B1 and B2) should have the same length
		//TODO: check if right angle between A and B ?
		
		float[] deviation = new float[4];
		
		//vertical symmetry-line 
		//C1 = A1x+B1y
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
		
		start[0] = points[PN.NOSE_LEFT].x;
		start[1] = points[PN.NOSE_LEFT].y;
		end[0] = points[PN.NOSE_RIGHT].x;
		end[1] = points[PN.NOSE_RIGHT].y;	
		deviation[1] = symRatio(start,end,intersectionPoint(A1, B1, C1, start, end));
		
		start[0] = points[PN.EYE_LEFT_OUTSIDE].x;
		start[1] = points[PN.EYE_LEFT_OUTSIDE].y;
		end[0] = points[PN.EYE_RIGHT_OUTSIDE].x;
		end[1] = points[PN.EYE_RIGHT_OUTSIDE].y;	
		deviation[2] = symRatio(start,end,intersectionPoint(A1, B1, C1, start, end));
		
		start[0] = points[PN.EYE_LEFT_INSIDE].x;
		start[1] = points[PN.EYE_LEFT_INSIDE].y;
		end[0] = points[PN.EYE_RIGHT_INSIDE].x;
		end[1] = points[PN.EYE_RIGHT_INSIDE].y;	
		deviation[3] = symRatio(start,end,intersectionPoint(A1, B1, C1, start, end));
		
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
		float diff = lengthHalf / (length/2);
		if(diff>1)	diff-=1;
		else		diff = 1-diff;
		
		return diff*100;
	}
	
	//array-length of start and end == [2] with x on [0] and y on [1]
	//A1,B1,C1 coeff. of the symmetry line
	private float[] intersectionPoint(int A1, int B1, int C1, float[] start, float[] end){
		float A2 = end[1]-start[1];
		float B2 = start[0]-end[0];
		float C2 = A2*start[0]+B2*start[1];
		
		float det = A1*B2 - A2*B1;
		if(det == 0.0f) return null; //should never happen
		
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
		Log.e("TULLE", "eyeleft WIDTH: "+ Math.abs(points[PN.EYE_RIGHT_INSIDE].x - points[PN.EYE_LEFT_INSIDE].x));
		return Math.abs(points[PN.EYE_RIGHT_INSIDE].x - points[PN.EYE_LEFT_INSIDE].x);
	}
	
	private int eyeLeftWidth(){
		Log.e("TULLE", "eyeleft WIDTH: "+ Math.abs(points[PN.EYE_LEFT_OUTSIDE].x - points[PN.EYE_LEFT_INSIDE].x));
		return Math.abs(points[PN.EYE_LEFT_OUTSIDE].x - points[PN.EYE_LEFT_INSIDE].x);
	}
	
	private int eyeRightWidth(){
		Log.e("TULLE", "eyeright WIDTH: "+ Math.abs(points[PN.EYE_RIGHT_OUTSIDE].x - points[PN.EYE_RIGHT_INSIDE].x));
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
