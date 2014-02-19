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
package com.tuwien.snowwhite.celebrityData;

import com.tuwien.snowwhite.beautyCalculation.FeaturePoints;

import android.graphics.drawable.Drawable;

public class Celebrity implements ICelebrity{
	
	private String name;
	private String sex;
	private float score;
	private Drawable picture;
	private FeaturePoints[] points;
	
	public Celebrity(String name, String sex, float score, Drawable picture, FeaturePoints[] points){
		this.name = name;
		this.sex = sex;
		this.score = score;
		this.picture = picture;
		this.points = points;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSex() {
		return sex;
	}

	@Override
	public float getScore() {
		return score;
	}
	
	public void setScore(float s){
		score = s;
	}
	
	@Override
	public FeaturePoints[] getPoints() {
		return points;
	}

	@Override
	public Drawable getPicture() {
		return picture;
	}
	

}
