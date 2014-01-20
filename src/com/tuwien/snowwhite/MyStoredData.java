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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

//singleton to work with a single instance of shared preferences on the whole project
public class MyStoredData {
	
	 private static MyStoredData instance = null;
	 private SharedPreferences sharedPref = null;
	 private SharedPreferences.Editor editor = null;
	 
	 protected MyStoredData() {}
	   public static MyStoredData getInstance() {
	      if(instance == null) {
	         instance = new MyStoredData();
	      }
	      return instance;
	   }
	   
	   public void Initialize(Context c){
		   sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
		   editor = sharedPref.edit();
	   }
	   
	   public SharedPreferences.Editor getEditor(){
		   return editor;
	   }
	   
	   public SharedPreferences getSharedPreferences(){
		   return sharedPref;
	   }

}
