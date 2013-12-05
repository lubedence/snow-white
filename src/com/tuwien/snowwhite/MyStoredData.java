package com.tuwien.snowwhite;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
