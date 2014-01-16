package com.tuwien.snowwhite;


import android.app.Activity;
import android.os.Bundle; 

import java.io.*;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.app.ProgressDialog;
import android.transition.Visibility;
import android.util.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Looper;

public class FacialFeatureActivity extends Activity {	   
	private static final String TAG = "FacialFeatureActivity";
	private final boolean debug = true;
	
	//handler message codes
	private static final int CALCULATION_SUCCESSFUL = 0x101; //facial feature detection successful
	private static final int CALCULATION_ERROR = 0x102; //facial feature detection not successful (no face found or coudn't process/open image file) 
	
  	//string-id's of the data stored inside the intent
  	public final static String IMAGEPATH = "imgpath";
  	public final static String FEATUREARRAY = "featurearray";
	
	private ProgressDialog pd = null;
	private SampleView sv; 			//used for displaying processed image and (if successful) facial features
  	private Bitmap mImage = null;   //image to be processed/displayed on SampleView

  	//displayed image is a scaled version of the processed one.
  	//the following two values are the scale-ratios of the image's height and width
  	//used for drawing the facial features on the right position
  	private float ratioW = 0;
  	private float ratioH = 0;
  	
  	//points of the calculated facial features
  	//points[0|2|4|...] => x, points[1|3|5|...] => y
  	private int[] points;
  	
  	//path to the stored image
  	private String imgPath = "";
  	
  	//true after complete calculation
  	private boolean openCVLoaded = false;
  	
  	//error flag (problem with read/write face detection xml files)
  	private boolean error = false;
  	
  	//jni-function to calculate facial features
    public native int[] FindFaceLandmarks(String path);
    
    
    //called after OpenCV is loaded successfully
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    if (debug) Log.e(TAG, "OpenCV loaded successfully");
                    System.loadLibrary("native_sample");
                    openCVLoaded = true;
                    processing();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };
    
    private Handler myViewUpdateHandler = new Handler(){ 
    	// @Override 
    	public void handleMessage(Message msg) { 
    		switch (msg.what) { 
    			case FacialFeatureActivity.CALCULATION_SUCCESSFUL:
    				sv.invalidate();
    				((Button) findViewById(R.id.button_next)).setVisibility(View.VISIBLE);
    				try {pd.dismiss(); pd = null;} catch (Exception e){}
    			case FacialFeatureActivity.CALCULATION_ERROR:
    				sv.invalidate();
    				try {pd.dismiss(); pd = null;} catch (Exception e){}
    			break;
    		} 
    		super.handleMessage(msg); 
    	} 
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facial_feature);
		
		//get image file path
		Intent intent = getIntent();
		imgPath = intent.getStringExtra("imgFile");
		
		//xml files used by stasm-libary for face detection
		File dataDir = getDir("stasmdata", Context.MODE_PRIVATE);
		File f_frontalface = new File(dataDir, "haarcascade_frontalface_alt2.xml");
		File f_lefteye     = new File(dataDir, "haarcascade_mcs_lefteye.xml");
		File f_righteye    = new File(dataDir, "haarcascade_mcs_righteye.xml");
		
		//put this files in the local directory
        if (!(f_frontalface.exists() && f_lefteye.exists() && f_righteye.exists())) {
        	putDataFileInLocalDir(R.raw.haarcascade_frontalface_alt2, f_frontalface);
        	putDataFileInLocalDir(R.raw.haarcascade_mcs_lefteye, f_lefteye);
        	putDataFileInLocalDir(R.raw.haarcascade_mcs_righteye, f_righteye);
        }
        
        //this view displays the image and facial features
        sv = (SampleView) findViewById(R.id.sv);
        
		Initialize();      
	}
	
	
	 private void putDataFileInLocalDir(int id, File f) {
	        try {
	            InputStream is = getResources().openRawResource(id);
	            FileOutputStream os = new FileOutputStream(f);
	            byte[] buffer = new byte[4096];
	            int bytesRead;
	            while ((bytesRead = is.read(buffer)) != -1) {
	                os.write(buffer, 0, bytesRead);
	            }
	            is.close();
	            os.close();
	        } catch (IOException e) {
	        	if (debug) Log.e(TAG, "Error on putDataFileInLocalDir: "+e.toString());
	            Toast.makeText(this, "Error on read/write face-detection files!", Toast.LENGTH_LONG).show();
	            error = true;
	        }
	        
	    }
	
    
    @Override
    protected void onDestroy() {
    	if(sv != null){
    		sv.clearBitmap();
    	}
    	if(mImage != null){
    		mImage.recycle();
    		mImage = null;
    	}
        super.onDestroy();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if(!error && !openCVLoaded)
        	OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_5, this, mLoaderCallback);
    }
		
	private void processing() {
		//create progress dialog
		if (pd == null) 
			pd = ProgressDialog.show(this, null, getString(R.string.processing));
		
		new Thread(new Runnable() { 
			public void run() {
				Looper.prepare(); 
				
				Message m = new Message();
				m.what = FacialFeatureActivity.CALCULATION_ERROR;
				
				if (mImage != null) {		
					//call native function and calculate facial features 
					points = FindFaceLandmarks(imgPath);

					//handle possible error
					if ((points[0] == -1) && (points[1] == -1)) {
						Toast.makeText(FacialFeatureActivity.this, getString(R.string.stasm_error_loading), Toast.LENGTH_LONG).show();
					} else if ((points[0] == -2) && (points[1] == -2)) {
						Toast.makeText(FacialFeatureActivity.this, getString(R.string.stasm_error_processing), Toast.LENGTH_LONG).show();
					} else if ((points[0] == -3) && (points[1] == -3)) {
						Toast.makeText(FacialFeatureActivity.this, getString(R.string.stasm_error_finding), Toast.LENGTH_LONG).show();	
						sv.setBM(mImage);
					} else {
						//facial features found. draw them on the image
						sv.setBM(mImage, points, ratioW, ratioH);
						m.what = FacialFeatureActivity.CALCULATION_SUCCESSFUL;
					}
				}

				FacialFeatureActivity.this.myViewUpdateHandler.sendMessage(m);
				Looper.loop();
			}
		}).start();
	}
	
	//back to CamActivity. free memory
	public void goBack(View view){
		if(sv != null){
    		sv.clearBitmap();
    	}
    	if(mImage != null){
    		mImage.recycle();
    		mImage = null;
    	}
		finish();
	}
	
	//go to next activity (ResultActivity)
	public void goNext(View view){
		Intent intent = new Intent(this, ResultActivity.class);
	    intent.putExtra(FEATUREARRAY, points);
	    intent.putExtra(IMAGEPATH, imgPath);
	    startActivity(intent);
	}
    
	void Initialize() {
		try {
			//get image as Bitmap
			Bitmap temp = BitmapFactory.decodeFile(imgPath);
			
			//get display size
			Point outSize = new Point();
			((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(outSize);; 
			
			//scale image to display with calculated facial features on screen
			int finalImgWidth  = outSize.x;
			int finalImgHeight = temp.getHeight() * finalImgWidth / temp.getWidth();
			mImage = Bitmap.createScaledBitmap(temp, finalImgWidth, finalImgHeight, true);
			int mWidth = mImage.getWidth();
			int mHeight= mImage.getHeight();
			ratioW = (float)mWidth/temp.getWidth();
			ratioH = (float)mHeight/temp.getHeight();
			//free memory
			temp.recycle();
			temp = null;
			
			if (debug) Log.d(TAG, "Scaled Image: "+ mWidth+"X"+mHeight+" "+ratioW+" "+ratioH);

			//show image on screen (no facial features yet)
			sv.setBM(mImage);
			sv.invalidate();
		} catch (Exception e) {
			Toast.makeText(this, "Error on initialization", Toast.LENGTH_LONG).show();
			if (debug) Log.e(TAG, "On Initialize:"+e.toString());
		} catch (OutOfMemoryError oe) {
			Toast.makeText(this, "Not enough memory", Toast.LENGTH_LONG).show();
			if (debug) Log.e(TAG, "On Initialize:"+oe.toString());
		}
	}
}
