package com.tuwien.snowwhite;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class SettingsActivity extends Activity implements OnItemSelectedListener{
	
	private Spinner spinner;
	private int[] arr_value;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		//addListenerOnCheckBoxes();
		
		int c = 0;
		arr_value = new int[this.getResources().getStringArray(R.array.setting_imgSize_values).length];
		for (String s : this.getResources().getStringArray(R.array.setting_imgSize_values)){
			arr_value[c] = Integer.parseInt(s);
			c++;
		}
			
		spinner = (Spinner) findViewById(R.id.setting_imgSize_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.setting_imgSize_desc, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}
	
	@Override
	protected void onStart() {
		super.onRestart();
		spinner.setSelection(getImageSizeId());
	}

	public void backToMain(View view){
		  finish();
	  }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		try{
			 MyStoredData.getInstance().getEditor().putInt(getString(R.string.settings_size_value_id), pos);
			 if(pos<arr_value.length)
				MyStoredData.getInstance().getEditor().putInt(getString(R.string.settings_size_value), arr_value[pos]);
			 MyStoredData.getInstance().getEditor().commit();
		} catch(Exception e){Log.d("SAVE SETTINGS",e.toString());}
	}
	
	
	private int getImageSizeId(){
			return MyStoredData.getInstance().getSharedPreferences().getInt(getString(R.string.settings_size_value_id), (int)Math.floor(arr_value.length / 2));
	}
	


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	
	/*
	 public void addListenerOnCheckBoxes() {
		 
		 	CheckBox cb = (CheckBox) findViewById(R.id.setting_ff_check);
		 
		 	cb.setOnClickListener(new OnClickListener() {
			  public void onClick(View v) {
				  MyStoredData.getInstance().getEditor().putBoolean(getString(R.string.settings_ff), ((CheckBox) v).isChecked());
				  MyStoredData.getInstance().getEditor().commit();
				  Log.d("STORING", ""+MyStoredData.getInstance().getSharedPreferences().getBoolean(getString(R.string.settings_ff), true));
			  }
			});
		 
		  }*/
	
}
