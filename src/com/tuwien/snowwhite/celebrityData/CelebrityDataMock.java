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
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class CelebrityDataMock implements ICelebrityData{
	
	private ArrayList<ICelebrity> celData = new ArrayList<ICelebrity>();
	private ArrayList<ICelebrity> celDataF = new ArrayList<ICelebrity>();
	private ArrayList<ICelebrity> celDataM = new ArrayList<ICelebrity>();
	
	public CelebrityDataMock(Context c){
		
		//create some celebrities
		String name = "Justin Bieber";
		Drawable pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 72.3f, pic));
		
		name = "Angelina Jolie";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 77.9f, pic));
		
		name = "Brad Pitt";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 79.1f, pic));
		
		name = "Lady Gaga";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 68.5f, pic));
		
		name = "Miley Cyrus";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 76.3f, pic));
		
		name = "Katy Perry";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 74.2f, pic));
		
		name = "Robert Redford";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 75.4f, pic));
		
		name = "Barack Obama";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 68.4f, pic));
		
		name = "Steve Buscemi";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 64.7f, pic));
		
		name = "Angela Merkel";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 70.6f, pic));
		
		name = "Nicolas Cage";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 71.3f, pic));
		
		name = "Bill Gates";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 69.7f, pic));
		
		name = "George Clooney";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 81.2f, pic));
		
		name = "Marilyn Monroe";
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 72.0f, pic));
		
		pic = null;
		
		
		//order in descending order
		Collections.sort(celData, new Comparator<ICelebrity>(){
			 public int compare(ICelebrity c1, ICelebrity c2) {
				 return Float.compare(c2.getScore(), c1.getScore());
			    }
		});
		
		for(ICelebrity cc: celData){
			Log.d("DESC::::", cc.getScore()+"");
		}
	}

	@Override
	public ArrayList<ICelebrity> getAllCelebritiesDescOrder() {
		return celData;
	}

	@Override
	public ArrayList<ICelebrity> getAllFemaleCelebritiesDescOrder() {
		if(celDataF.size() == 0)
			for(ICelebrity c: celData){
				if(c.getSex() == "female")
					celDataF.add(c);
			}
		return celDataF;
	}

	@Override
	public ArrayList<ICelebrity> getAllMaleCelebritiesDescOrder() {
		if(celDataM.size() == 0)
			for(ICelebrity c: celData){
				if(c.getSex() == "male")
					celDataM.add(c);
			}
		return celDataM;
	}
	
	

}
