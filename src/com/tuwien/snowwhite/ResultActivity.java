package com.tuwien.snowwhite;

import java.util.zip.DataFormatException;

import com.tuwien.snowwhite.beautyCalculation.FacialFeatures;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {
	
	private String imgPath="";
	private int[] facialData;
	
	private FacialFeatures ff;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		Intent intent = getIntent();
		imgPath = intent.getStringExtra(MainActivity.IMAGEPATH);
		facialData = intent.getIntArrayExtra(MainActivity.FEATUREARRAY);
		
		try {
			ff = new FacialFeatures(facialData);
		} catch (DataFormatException e) {
			Toast.makeText(ResultActivity.this, "Error parsing facial features", Toast.LENGTH_LONG).show();
			e.printStackTrace();
			finish();
		}
		
		float[] diff = ff.featureRatios();
		int count=diff.length;
		String out="Diff in %\n";
		float total = 0f;
		for (int i=0; i<diff.length; i++){
			out+=diff[i]+"%\n";
			total+=diff[i];
		}
		
		out+="\nSymmetry: \n";
		diff = ff.checkSymmetry();
		for (int i=0; i<diff.length; i++){
			out+=diff[i]+"%\n";
			total+=diff[i];
		}
		
		total = total/(diff.length+count);
		total = 100-total;
		out+="\nbeauty: "+total+"/100\n";
		
		TextView txtView = (TextView) findViewById(R.id.text_id);
		txtView.setText(out);
	}

}
