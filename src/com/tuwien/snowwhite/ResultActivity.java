package com.tuwien.snowwhite;

import java.util.zip.DataFormatException;

import com.tuwien.snowwhite.beautyCalculation.FacialFeatures;
import com.tuwien.snowwhite.celebrityData.CelebrityDataMock;
import com.tuwien.snowwhite.celebrityData.ICelebrity;
import com.tuwien.snowwhite.celebrityData.ICelebrityData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {
	
	private String imgPath="";
	private int[] facialData;
	
	private FacialFeatures ff;
	private float result = 0.0f;
	
	private LinearLayout contentContainer = null;
	private LinearLayout contentContainer_cel = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		Intent intent = getIntent();
		imgPath = intent.getStringExtra(MainActivity.IMAGEPATH);
		facialData = intent.getIntArrayExtra(MainActivity.FEATUREARRAY);
		
		try {
			ff = new FacialFeatures(facialData,this);
		} catch (DataFormatException e) {
			Toast.makeText(ResultActivity.this, "Error parsing facial features", Toast.LENGTH_LONG).show();
			e.printStackTrace();
			finish();
		}
		
		
		startDetails();
		startCelebrities();
	}
	
	
	private void startDetails(){

		contentContainer =  ((LinearLayout)findViewById(R.id.result_content));
		contentContainer.setVisibility(View.VISIBLE);
		
		addDetailRow(getString(R.string.resultlist_gRatio_error), 5);
		
		float[] diff = ff.featureRatios();
		String[] txt = ff.featureRatiosText();
		
		int count=diff.length;
		float total = 0f;
		float max = 0.0f;
		float min = 100.0f;
		
		for (int i=0; i<diff.length; i++){
			float tmp = diff[i];
			if(tmp>max)
				max = tmp;
			if(tmp<min)
				min = tmp;
			total+=tmp;
		}
		float mean = total/count;
		//red green for: min - green
		float green = min + (mean-min)/3;
		//green red for: red - max
		float red = mean + (max - mean)/3;
		for (int i=0; i<diff.length; i++){
			float tmp = diff[i];
			if(tmp<green)
				addDetailRow(txt[i],tmp,Color.GREEN);
			else if(tmp>red)
				addDetailRow(txt[i],tmp,Color.RED);
			else
				addDetailRow(txt[i],tmp,Color.GRAY);
		}
		
		
		addDetailRow(getString(R.string.resultlist_fSymmetry_error), 15);
		
		diff = ff.checkSymmetry();
		txt = ff.symmetryText();
		max = 0.0f;
		min = 100.0f;
		float tmpTotal = 0;
		for (int i=0; i<diff.length; i++){
			float tmp = diff[i];
			if(tmp>max)
				max = tmp;
			if(tmp<min)
				min = tmp;
			total+=tmp;
			tmpTotal+=tmp;
		}
		mean = tmpTotal/diff.length;
		//red green for: min - green
		green = min + (mean-min)/3;
		//green red for: red - max
		red = mean + (max - mean)/3;
		for (int i=0; i<diff.length; i++){
			float tmp = diff[i];
			if(tmp<green)
				addDetailRow(txt[i],tmp,Color.GREEN);
			else if(tmp>red)
				addDetailRow(txt[i],tmp,Color.RED);
			else
				addDetailRow(txt[i],tmp,Color.GRAY);
		}
				
		total = total/(diff.length+count);
		total = 100-total;
		result = (float)Math.round(total*10) / 10.0f;
		
		TextView title = (TextView)findViewById(R.id.result_title);
		try{
			String[] arr_smiley = this.getResources().getStringArray(R.array.smiley_categories);
			int category = 0;
			for(String s : arr_smiley){
				int tmp = Integer.parseInt(s);
				if(result >= tmp && category<=tmp)
					category = tmp;
			}
			Drawable smiley = getResources().getDrawable(getResources().getIdentifier("ic_smiley_"+category, "drawable", getPackageName()));
			title.setCompoundDrawablesWithIntrinsicBounds(null, null, smiley, null);
		}catch(Exception e){Log.d("Smiley retrieval", e.toString());}
		title.setText(result+"%");
	}
	
	private void addDetailRow(String desc, float score, int color){
		
		score = (float)Math.round(score*10) / 10.0f;
		
		RelativeLayout rl = new RelativeLayout(this);
		rl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		int paddingDp = (int)(5 * this.getResources().getDisplayMetrics().density);
		rl.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
		
		TextView tv1 = new TextView(this);
		tv1.setText(desc);
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		tv1.setLayoutParams(params1);
		tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
		rl.addView(tv1);
				
		TextView tv2 = new TextView(this);
		tv2.setText(score+"%");
		tv2.setTextColor(color);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		tv2.setLayoutParams(params2);
		tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
		rl.addView(tv2);
		
		contentContainer.addView(rl);
		contentContainer.addView(divider());
	}
	
	
private View divider(){
	View v = new View(this);
	int lineHeight = (int)(this.getResources().getDisplayMetrics().density);
	v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,lineHeight));
	v.setBackgroundColor(Color.LTGRAY);
	
	return v;
}
	
private void addDetailRow(String desc, int paddingTop){
		
		int padding = (int)(5 * this.getResources().getDisplayMetrics().density);
		paddingTop = (int)(paddingTop * this.getResources().getDisplayMetrics().density);

		TextView tv = new TextView(this);
		tv.setText(desc);
		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		tv.setPadding(padding, paddingTop, padding, padding);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);

		contentContainer.addView(tv);
	}
	

private void startCelebrities(){
	contentContainer_cel =  ((LinearLayout)findViewById(R.id.result_content_cel));
	
	ICelebrityData celData = new CelebrityDataMock(this);
	for (ICelebrity  c : celData.getAllCelebrities())
		addCelRow(c.getName(),c.getScore(),c.getPicture());
	
}


private void addCelRow(String name, float score, Drawable picture){
	score = (float)Math.round(score*10) / 10.0f;
	
	RelativeLayout rl = new RelativeLayout(this);
	rl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
	int paddingDp = (int)(5 * this.getResources().getDisplayMetrics().density);
	rl.setPadding(2*paddingDp, paddingDp, 2*paddingDp, paddingDp);
	
	ImageView iv = new ImageView(this);
	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(this.getResources().getDisplayMetrics().density) * 200,(int)(this.getResources().getDisplayMetrics().density) * 200);
	params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
	iv.setLayoutParams(params);
	iv.setScaleType(ScaleType.CENTER_CROP);
	iv.setImageDrawable(picture);
	rl.addView(iv);
	
	TextView tv1 = new TextView(this);
	tv1.setText(name);
	RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	params1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
	params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
	tv1.setLayoutParams(params1);
	tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
	rl.addView(tv1);
			
	TextView tv2 = new TextView(this);
	tv2.setText(score+"%");
	RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
	params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
	tv2.setLayoutParams(params2);
	tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
	rl.addView(tv2);
	
	contentContainer_cel.addView(rl);
	contentContainer_cel.addView(divider());
}



	
	public void back(View view){
		  finish();
	  }
	
	public void goHome(View view){
		Intent intent = new Intent(this, StartActivity.class);
		startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	}
	
	public void goCelebrities(View view){
		contentContainer.setVisibility(View.GONE);
		contentContainer_cel.setVisibility(View.VISIBLE);
		}
	
	public void goDetails(View view){
		contentContainer_cel.setVisibility(View.GONE);
		contentContainer.setVisibility(View.VISIBLE);
		}

}
