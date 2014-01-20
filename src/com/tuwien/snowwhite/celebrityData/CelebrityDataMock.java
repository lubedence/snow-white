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

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class CelebrityDataMock implements ICelebrityData{
	
	private ArrayList<ICelebrity> celData = new ArrayList<ICelebrity>();
	private ArrayList<ICelebrity> celDataF = new ArrayList<ICelebrity>();
	private ArrayList<ICelebrity> celDataM = new ArrayList<ICelebrity>();
	
	public CelebrityDataMock(Context c){
		
		//create some celebrities
		String name = "Justin Bieber";
		Drawable pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 86.2f, pic));
		
		name = "Angelina Jolie";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 91.3f, pic));
		
		name = "Brad Pitt";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 88.9f, pic));
		
		name = "Lady Gaga";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 89.1f, pic));
		
		name = "Miley Cyrus";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 90.4f, pic));
		
		name = "Katy Perry";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 88.6f, pic));
	}

	@Override
	public ArrayList<ICelebrity> getAllCelebrities() {
		return celData;
	}

	@Override
	public ArrayList<ICelebrity> getAllFemaleCelebrities() {
		if(celDataF.size() == 0)
			for(ICelebrity c: celData){
				if(c.getSex() == "female")
					celDataF.add(c);
			}
		return celDataF;
	}

	@Override
	public ArrayList<ICelebrity> getAllMaleCelebrities() {
		if(celDataM.size() == 0)
			for(ICelebrity c: celData){
				if(c.getSex() == "male")
					celDataM.add(c);
			}
		return celDataM;
	}
	
	

}
