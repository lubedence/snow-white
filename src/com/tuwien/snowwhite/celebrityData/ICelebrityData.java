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

import com.tuwien.snowwhite.beautyCalculation.FeaturePoints;

public interface ICelebrityData {
	
	public ArrayList<ICelebrity> getAllCelebritiesDescOrder();
	public ArrayList<ICelebrity> getAllFemaleCelebritiesDescOrder();
	public ArrayList<ICelebrity> getAllMaleCelebritiesDescOrder();
	
	public  ArrayList<ICelebrity> getCelebritiesDescOrder(FeaturePoints[] points, int bestX);

}

