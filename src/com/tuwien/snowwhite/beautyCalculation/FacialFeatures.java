package com.tuwien.snowwhite.beautyCalculation;

import java.util.zip.DataFormatException;

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
	
	public FacialFeatures(int[] p) throws DataFormatException{
		if(p.length != FEATURECOUNT*2) 
			throw new DataFormatException("Wrong array length. Should be "+FEATURECOUNT+"*2");
		
		for(int i=0; i<FEATURECOUNT; i++) 
			points[i] = new FeaturePoints(p[2*i], p[2*i+1]);	
	}
	
	private void featureRatios(){
		
		//VERTICAL------------------------------------------------------------------
		
		//eye center - nose bottom
		int eye_nose = points[PN.NOSE_BOTTOM].y - eyeHeight();
		//nose bottom - mouth bottom
		int nose_mouth = points[PN.MOUTH_BOTTOM].y - points[PN.NOSE_BOTTOM].y;
		
		//eye center - mouth center
		int eye_mouth = mouthCenterHeight() - eyeHeight();
		//mouth center - chin bottom
		int mouth_center_chin = points[PN.FACE_BOTTOM].y - mouthCenterHeight();
		
			//mouth top - mouth bottom
			int mouth_top_bottom = points[PN.MOUTH_BOTTOM].y - mouthTopHeight();
			//mouth bottom - chin bottom
			int mouth_bottom_chin = points[PN.FACE_BOTTOM].y - points[PN.MOUTH_BOTTOM].y;
		
		//face top - nose peak
		int face_top_nose = points[PN.NOSE_PEAK].y - points[PN.FACE_TOP].y; 
		//nose peak - chin bottom
		int nose_chin = points[PN.FACE_BOTTOM].y - points[PN.NOSE_PEAK].y; 
		
		
		
		//HORIZONTAL---------------------------------------------------------------
		
		//1.00
		int nose_width = noseWidth();
		//phi^2
		int eyes_outer_dist = eyesOuterDistance();
		//phi^3
		int face_width = faceWidth();
		
		//right eye width - left eye width
		int right_left_eye_width = Math.abs(eyeLeftWidth() - eyeRightWidth());
		//eye width - space between eyes
		int eye_width_space_between = Math.abs((eyeLeftWidth() + eyeRightWidth())/2 - eyesInnerDistance());
		
		//mouth width / space between eyes
		float eyes_center_mouth_width = mouthWidth() / eyesInnerDistance();
		
		//mouth width / nose width
		float nose_mouth_width = mouthWidth() / noseWidth();
		
		//BOTH---------------------------------------------------------------------
		
		//face height / face width
		float face_height_width = faceHeight() / faceWidth();
		
		
	}
	
	private void checkSymmetry(){
		//line from face top (or nose center? cause, face top X could be not that accurate) to face bottom -> A
		//for ex.: line from mouth left to right -> B
		//calc intersection point of A and B
		// http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=geometry2#line_line_intersection
		//both part lines(B1 and B2) should have the same length
		//right angle between A and B
	}
	
	
	private int faceWidth(){
		return points[PN.FACE_RIGHT].x - points[PN.FACE_LEFT].x;
	}
	
	private int faceHeight(){
		return points[PN.FACE_TOP].y - points[PN.FACE_BOTTOM].y;
	}
	
	private int eyesOuterDistance(){
		return points[PN.EYE_RIGHT_OUTSIDE].x - points[PN.EYE_LEFT_OUTSIDE].x;
	}
	
	private int eyesInnerDistance(){
		return points[PN.EYE_RIGHT_INSIDE].x - points[PN.EYE_LEFT_INSIDE].x;
	}
	
	private int eyeLeftWidth(){
		return points[PN.EYE_LEFT_OUTSIDE].x - points[PN.EYE_LEFT_INSIDE].x;
	}
	
	private int eyeRightWidth(){
		return points[PN.EYE_RIGHT_OUTSIDE].x - points[PN.EYE_RIGHT_INSIDE].x;
	}
	
	private int noseWidth(){
		return points[PN.NOSE_RIGHT].x - points[PN.NOSE_LEFT].x;
	}
	
	private int mouthWidth(){
		return points[PN.MOUTH_RIGHT].x - points[PN.MOUTH_LEFT].x;
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
