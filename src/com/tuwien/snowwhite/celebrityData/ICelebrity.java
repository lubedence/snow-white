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

public interface ICelebrity {
	
	public String getName();
	public String getSex();
	public float getScore();
	public void setScore(float s);
	public Drawable getPicture();
	FeaturePoints[] getPoints();

}

/* Hier variable mit facial-features array hinzufügen. (Celeb erneut abfotografieren, falls maske + wertung ~stimmt, dann die arraywerte ablesen und 
 * statisch speichern
 * Dann bei result activity user array mit celeb. arrays vergleichen -> geeigneter algorithmus um top 5 eukl. distanzen zu finden
 * Diese anschließend anzeigen
 */
