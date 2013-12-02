package com.tuwien.snowwhite;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	public void backToMain(View view){
		  finish();
	  }
	
}
