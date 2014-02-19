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
package com.tuwien.snowwhite;

import com.tuwien.snowwhite.beautyCalculation.FeaturePoints;
import com.tuwien.snowwhite.celebrityData.SimilarityCalculation;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		
		/*FeaturePoints[] sourcePoints = new FeaturePoints[6];
		FeaturePoints[] targetPoints = new FeaturePoints[6];
		sourcePoints[0] = new FeaturePoints(8,5);
		sourcePoints[1] = new FeaturePoints(5,8);
		sourcePoints[2] = new FeaturePoints(5,11);
		sourcePoints[3] = new FeaturePoints(8,12);
		sourcePoints[4] = new FeaturePoints(11,11);
		sourcePoints[5] = new FeaturePoints(11,8);
		
		targetPoints[0] = new FeaturePoints(15,9);
		targetPoints[1] = new FeaturePoints(12,10);
		targetPoints[2] = new FeaturePoints(12,14);
		targetPoints[3] = new FeaturePoints(13,15);
		targetPoints[4] = new FeaturePoints(17,15);
		targetPoints[5] = new FeaturePoints(17,12);
		
		
		SimilarityCalculation sc = new SimilarityCalculation(sourcePoints);
		
		sc.getSimilarity(targetPoints);*/
	}
	
	public void backToMain(View view){
		  finish();
	  }

}
