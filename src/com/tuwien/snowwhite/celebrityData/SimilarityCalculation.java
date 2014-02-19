package com.tuwien.snowwhite.celebrityData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;

import com.tuwien.snowwhite.beautyCalculation.FeaturePoints;
import com.tuwien.snowwhite.beautyCalculation.PN;

public class SimilarityCalculation {
	
	private FeaturePoints[] sourceP;
	private List<Integer> ignoreList;
	
	public SimilarityCalculation(FeaturePoints[] sourcePoints){
		sourceP = alignPoints(centerPoints(sourcePoints));
		//logPoints(sourcePoints);
		
		ignoreList = Arrays.asList(31,32,33,35,36,37,38,39,16,17,19,20,41,42,43,45,46,47,23,24,26,27,66,67,68,69,70,71);
	}
	
	public float getSimilarity(FeaturePoints[] points){
		if(points.length != sourceP.length)
			return -1;
		
		FeaturePoints[] destP = alignPoints(centerPoints(scalePoints(points)));
		//logPoints(destP);
		
		float diffSum = 0;
		
		for(int i=0; i<sourceP.length; i++){
			if(ignoreList.contains(i))
				continue;
			
			int deltaX = Math.abs(sourceP[i].x) - Math.abs(destP[i].x);
			int deltaY = Math.abs(sourceP[i].y) - Math.abs(destP[i].y);
			
			float diff = (float) Math.sqrt(( Math.pow(deltaX, 2) + Math.pow(deltaY, 2) ));
			//Log.d("SIMILARITY", "diff: "+diff);
			
			diffSum += (float) Math.exp(-diff);
			//Log.d("SIMILARITY", "diff|exp: "+Math.exp(-diff));
		}
		Log.d("SIMILARITY", "diff|SUM: "+diffSum);
		return diffSum;
	}
	
	public int usedFeatureAmount(){
		return sourceP.length - ignoreList.size();
	}
	
	private void logPoints(FeaturePoints[] p){
		for(int i=0; i<p.length; i++){
			Log.d("SIMILARITY", "Point "+i+": "+p[i].x+","+p[i].y);
		}
	}
	
	private FeaturePoints[] scalePoints(FeaturePoints[] p){
		int l_source = sourceP[PN.FACE_BOTTOM].y - sourceP[PN.FACE_TOP].y;
		int l_this = p[PN.FACE_BOTTOM].y - p[PN.FACE_TOP].y;
		float l_scaleFactor = (float)l_source / l_this;
		//Log.d("SIMILARITY", "scale lenght: "+l_scaleFactor);
		
		int w_source = sourceP[PN.FACE_RIGHT].x - sourceP[PN.FACE_LEFT].x;
		int w_this = p[PN.FACE_RIGHT].x - p[PN.FACE_LEFT].x;
		float w_scaleFactor = (float)w_source / w_this;
		//Log.d("SIMILARITY", "scale width: "+w_scaleFactor);
		
		float scaleFactor = (l_scaleFactor+w_scaleFactor)/2;
		
		for(int i=0; i<p.length; i++){
			p[i].x *= scaleFactor;
			p[i].y *= scaleFactor;
		}
		
		return p;
	}
	
	private FeaturePoints[] centerPoints(FeaturePoints[] p){
		FeaturePoints center = new FeaturePoints( p[PN.FACE_TOP].x+((p[PN.FACE_BOTTOM].x - p[PN.FACE_TOP].x)/2), p[PN.FACE_TOP].y+((p[PN.FACE_BOTTOM].y - p[PN.FACE_TOP].y)/2) );
		//Log.d("SIMILARITY", "center point: "+center.x+","+center.y);
		
		for(int i=0; i<p.length; i++){
			p[i].x -= center.x;
			p[i].y -= center.y;
		}
	
		
		return p;
	}
	
	private FeaturePoints[] alignPoints(FeaturePoints[] p){
		float deltaY = p[PN.FACE_TOP].y - p[PN.FACE_BOTTOM].y;
		float deltaX = p[PN.FACE_TOP].x - p[PN.FACE_BOTTOM].x;
		//Log.d("SIMILARITY", "deltaX/Y: "+deltaX+","+deltaY);
		
		if(deltaX == 0)
			return p;
		
		float atan = (float) Math.atan(deltaY/deltaX); //in radians
		float angle;
		if(atan<0)
			angle = (float) ((-Math.PI/2 - atan));
		else
			angle = (float) (Math.PI/2 - atan);

		//Log.d("SIMILARITY", "angle deg: "+Math.toDegrees(angle));
		float s = (float) Math.sin(angle);
		float c = (float) Math.cos(angle);
		
		for(int i=0; i<p.length; i++){
			int x = (int) (p[i].x * c - p[i].y * s);
			int y = (int) (p[i].x * s + p[i].y * c);
			
			p[i].x = x;
			p[i].y = y;
		}
		
		return p;
	}

}
