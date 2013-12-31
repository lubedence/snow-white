package com.tuwien.snowwhite;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class CelebrityActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_celebrity);
	}

	
	public void back(View view){
		  finish();
	  }
	
	public void goDetails(View view){
		finish();
	}
	
	public void goHome(View view){
		Intent intent = new Intent(this, StartActivity.class);
		startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	}
	

}
