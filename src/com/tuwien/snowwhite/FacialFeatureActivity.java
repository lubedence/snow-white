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
	private static String TAG = "FacialFeatureActivity";
	private static final int CALCULATION_SUCCESFUL = 0x101;
	private static final int CALCULATION_ERROR = 0x102;
	private boolean debug = true;
	private ProgressDialog pd = null;
	public static int screenWW;
	public static int screenHH;
	private SampleView sv;
  	private int	mWidth;
  	private int mHeight;
  	private Bitmap mImage = null;   //current bitmap, used when save
  	private Mat mRgba;
  	
  	private File dataDir = null;
  	private File f_frontalface = null;
  	private File f_lefteye = null;
  	private File f_righteye = null;
  	private File f_testface = null;
  	private float ratioW = 0;
  	private float ratioH = 0;
  	
  	private int[] points;
  	
  	private String imgPath = "";
  	
  	private boolean calculationComplete = false;
  	
  	public static String IMAGEPATH = "imgpath";
  	public static String FEATUREARRAY = "featurearray";
  		
    public native int[] FindFaceLandmarks(String path);
    
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    if (debug) Log.e(TAG, "OpenCV loaded successfully");
                    System.loadLibrary("native_sample");
                    processing();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };
    
    private boolean isDataFileInLocalDir(Context context) {
    	boolean ret = false;
        try {
        	dataDir = context.getDir("stasmdata", Context.MODE_PRIVATE);
        	f_frontalface = new File(dataDir, "haarcascade_frontalface_alt2.xml");
        	f_lefteye     = new File(dataDir, "haarcascade_mcs_lefteye.xml");
        	f_righteye    = new File(dataDir, "haarcascade_mcs_righteye.xml");
        	
        	ret = f_frontalface.exists() && f_lefteye.exists() && f_righteye.exists();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return ret;
    }
    
    private void putDataFileInLocalDir(Context context, int id, File f) {
    	if (debug) Log.e(TAG, "putDataFileInLocalDir: "+f.toString());
        try {
            InputStream is = context.getResources().openRawResource(id);
            FileOutputStream os = new FileOutputStream(f);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (debug) Log.e(TAG, "putDataFileInLocalDir: done!");
    }

    private Handler myViewUpdateHandler = new Handler(){ 
    	// @Override 
    	public void handleMessage(Message msg) { 
    		switch (msg.what) { 
    			case FacialFeatureActivity.CALCULATION_SUCCESFUL:
    				sv.invalidate();
    				calculationComplete = true;
    				((Button) findViewById(R.id.button_next)).setVisibility(View.VISIBLE);
    				try {pd.dismiss(); pd = null;} catch (Exception e){}
    			case FacialFeatureActivity.CALCULATION_ERROR:
    				sv.invalidate();
    				calculationComplete = true;
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
		
		//display size
		Point outSize = new Point();
		((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(outSize);; 
		screenWW = outSize.x;
		screenHH = outSize.y;	
		
		//put face detection files for stasm-lib in local directory
        if (!isDataFileInLocalDir(FacialFeatureActivity.this)) {
        	putDataFileInLocalDir(FacialFeatureActivity.this, R.raw.haarcascade_frontalface_alt2, f_frontalface);
        	putDataFileInLocalDir(FacialFeatureActivity.this, R.raw.haarcascade_mcs_lefteye, f_lefteye);
        	putDataFileInLocalDir(FacialFeatureActivity.this, R.raw.haarcascade_mcs_righteye, f_righteye);
        }
        
        //this view displays image and facial features
        sv = (SampleView) findViewById(R.id.sv);
        
		Initialize();      
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
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_5, this, mLoaderCallback);
    }
		
	private void processing() {
		//already processed (if onResume is called after processing)
		if(calculationComplete)
			return;
		
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
						sv.setBM(mImage, points, ratioW, ratioH);
						m.what = FacialFeatureActivity.CALCULATION_SUCCESFUL;
					}
				}

				FacialFeatureActivity.this.myViewUpdateHandler.sendMessage(m);
				Looper.loop();
			}
		}).start();
	}
	
	
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
			
			//scale image to display with calculated facial features on screen
			int finalImgWidth  = screenWW;
			int finalImgHeight = temp.getHeight() * finalImgWidth / temp.getWidth();
			mImage = Bitmap.createScaledBitmap(temp, finalImgWidth, finalImgHeight, true);
			mWidth = mImage.getWidth();
			mHeight= mImage.getHeight();
			ratioW = (float)mWidth/temp.getWidth();
			ratioH = (float)mHeight/temp.getHeight();
			temp.recycle();
			temp = null;
			
			if (debug) Log.e(TAG, "Scaled Image: "+ mWidth+"X"+mHeight+" "+ratioW+" "+ratioH);

			//show image on screen (no facial features yet)
			sv.setBM(mImage);
			sv.invalidate();
		} catch (Exception e) {
			Toast.makeText(this, "Error on initialization", Toast.LENGTH_LONG).show();
			if (debug) Log.e(TAG, "On Initialize:"+e.toString());
		} catch (OutOfMemoryError oe) {
			Toast.makeText(this, "Not enough memory", Toast.LENGTH_LONG).show();
		}
	}
}
