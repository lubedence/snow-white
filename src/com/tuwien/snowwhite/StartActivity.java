package com.tuwien.snowwhite;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		//init SharedPreferences
		//MyStoredData.getInstance().Initialize(this);
	}
	
	public void startMain(View view){
	    startActivity(new Intent(this, CamActivity.class)); 
	}
	
	public void startHelp(View view){
	    //startActivity(new Intent(this, HelpActivity.class)); 
		startActivity(new Intent(this, CelebrityActivity.class)); 
	}
	
	public void startSettings(View view){
		startActivity(new Intent(this, SettingsActivity.class)); 
	}
	

}
