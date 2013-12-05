package com.tuwien.snowwhite;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		//addListenerOnCheckBoxes();
		
		Spinner spinner = (Spinner) findViewById(R.id.setting_imgSize_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.setting_imgSize_spinner_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}

	public void backToMain(View view){
		  finish();
	  }
	
	 public void addListenerOnCheckBoxes() {
		 
		 	CheckBox cb = (CheckBox) findViewById(R.id.setting_ff_check);
		 
		 	cb.setOnClickListener(new OnClickListener() {
			  public void onClick(View v) {
				  MyStoredData.getInstance().getEditor().putBoolean(getString(R.string.settings_ff), ((CheckBox) v).isChecked());
				  MyStoredData.getInstance().getEditor().commit();
				  Log.d("STORING", ""+MyStoredData.getInstance().getSharedPreferences().getBoolean(getString(R.string.settings_ff), true));
			  }
			});
		 
		  }
	
}
