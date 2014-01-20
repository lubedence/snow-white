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

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		MyStoredData.getInstance().Initialize(this); //init SharedPreferences
	}
	
	public void startMain(View view){
		Toast.makeText(this, getString(R.string.button_main_start_loading), Toast.LENGTH_SHORT).show();
	    startActivity(new Intent(this, CamActivity.class)); 
	}
	
	public void startHelp(View view){
	    startActivity(new Intent(this, HelpActivity.class)); 
	}
	
	public void startSettings(View view){
		startActivity(new Intent(this, SettingsActivity.class)); 
	}
	

}
